package com.artemkaxboy.playground.gov.dumagovru

import com.artemkaxboy.playground.gov.dumagovru.dto.ApplicationDataDto
import com.artemkaxboy.playground.gov.dumagovru.dto.CommissionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.ConvocationDto
import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto
import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto.Companion.toEntities
import com.artemkaxboy.playground.gov.dumagovru.dto.FractionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.IntCommissionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.IntGroupDto
import com.artemkaxboy.playground.gov.dumagovru.dto.PersonDto
import com.artemkaxboy.playground.gov.dumagovru.dto.RegionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.StaffOrgDto
import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import com.artemkaxboy.playground.gov.dumagovru.entity.IntCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.IntGroup
import com.artemkaxboy.playground.gov.dumagovru.entity.Organisation
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import com.artemkaxboy.playground.gov.dumagovru.entity.Region
import com.artemkaxboy.playground.gov.dumagovru.entity.merge
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.OrganisationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

const val FILENAME = "duma-gov-ru/data.json"

val logger = KotlinLogging.logger {}

@Component
class DbInitializer(
    private val convocationRepository: ConvocationRepository,
    private val countryRepository: CountryRepository,
    private val organisationRepository: OrganisationRepository,
    private val intGroupRepository: IntGroupRepository,
    private val personRepository: PersonRepository,
    private val intCommissionRepository: IntCommissionRepository,
    private val regionRepository: RegionRepository,
) {

    @Transactional
    fun init() {

        val jsonString = getFileText(FILENAME)

        val json = Json { ignoreUnknownKeys = true }
        val data = json.decodeFromString<ApplicationDataDto>(jsonString)

        // todo save lastConvocation and version
        saveRegions(convertRegions(extractRegions(data)))

        val commissions = convertCommissions(extractCommissions(data))
        val fractions = convertFractions(extractFractions(data)) // need: nothing
        val staffOrgs = convertStaffOrgs(extractStaffOrgs(data))
        val organisations = commissions.merge(fractions + staffOrgs)
        saveOrganisations(organisations)
        saveConvocations(convertConvocations(extractConvocations(data))) // need: fraction

        val people = convertPeople(extractPeople(data), organisations)
//        val people = removeUnknownFractionPositions(convertPeople(extractPeople(data)), organisations)
        savePeople(people) // need: commission fraction convocation, inner: staffOrg

        saveCountries(convertCountries(extractCountries(data))) // need: nothing

        val intGroupEntities = convertIntGroups(extractIntGroups(data), people)
        saveIntGroups(intGroupEntities) // need: countries

        val intCommissionEntities = convertIntCommissions(extractIntCommissions(data))
        saveIntCommissions(intCommissionEntities)

        logger.info { "Database initialized" }
    }

    private fun extractPeople(data: ApplicationDataDto): Sequence<PersonDto> {
        return data.persons.asSequence()
    }

    private fun convertPeople(
        peopleDtos: Sequence<PersonDto>,
        organisations: Sequence<Organisation>
    ): Sequence<Person> {

        val organisationMap = organisations.associateBy { it.id }
        return peopleDtos.map { dto ->
            dto.toEntity(organisationMap)
        }
    }

    private fun removeUnknownFractionPositions(
        peopleEntities: Sequence<Person>,
        fractions: Sequence<Organisation>
    ): Sequence<Person> {
        val fractionIds = fractions.mapNotNull { it.id }.toSet() // Unable to find 72100025
        return peopleEntities.onEach { person ->
            person.fractionPositions.removeIf { fractionPosition ->
                fractionIds.contains(fractionPosition.fraction?.id).not()
            }
        }
    }

    private fun savePeople(people: Sequence<Person>) {
        personRepository.saveAll(people.asIterable())
    }

    private fun extractIntGroups(data: ApplicationDataDto): Sequence<IntGroupDto> {
        return data.intGroups.asSequence()
    }

    private fun convertIntGroups(
        intGroups: Sequence<IntGroupDto>,
        knownPeople: Sequence<Person>,
    ): Sequence<IntGroup> {

        val peopleMap = knownPeople.associateBy { it.id }
        val peopleByOriginalIdMap = knownPeople.associateBy { it.originalAisPersonId }

        return intGroups.map { it.toEntity() }
            .onEach { intGroup ->
                intGroup.intGroupPositions.onEach { pos ->
                    pos.people = pos.people
                        .mapNotNull { peopleMap[it.id] ?: peopleByOriginalIdMap[it.id] }
                        .toMutableSet()
                }
            }
    }

    private fun saveIntGroups(intGroups: Sequence<IntGroup>) {
        intGroupRepository.saveAll(intGroups.asIterable())
    }

    private fun extractConvocations(data: ApplicationDataDto): Sequence<ConvocationDto> {
        return data.convocations.asSequence()
    }

    private fun convertConvocations(convocations: Sequence<ConvocationDto>): Sequence<Convocation> {
        return convocations.map { it.toEntity() }
    }

    private fun saveConvocations(convocations: Sequence<Convocation>) {
        convocationRepository.saveAll(convocations.asIterable())
    }

    private fun extractStaffOrgs(data: ApplicationDataDto): Sequence<StaffOrgDto> {
        return data.persons.asSequence().mapNotNull { it.staffOrg }
    }

    private fun convertStaffOrgs(staffOrgs: Sequence<StaffOrgDto>): Sequence<Organisation> {
        return staffOrgs.map { it.toEntity() }
    }

    private fun extractFractions(data: ApplicationDataDto): Sequence<FractionDto> {
        val fromPositions = data.persons.asSequence().flatMap { it.fractionPositions }
            .map { FractionDto.minimal(it.org) }
        val direct = data.fractions.asSequence()
        return fromPositions + direct
    }

    private fun convertFractions(fractions: Sequence<FractionDto>): Sequence<Organisation> {
        return fractions.map { it.toEntity() }
    }

    private fun saveOrganisations(entities: Sequence<Organisation>) {
        organisationRepository.saveAll(entities.asIterable())
    }

    private fun extractIntCommissions(data: ApplicationDataDto): Sequence<IntCommissionDto> {
        return data.intCommissions.asSequence()
    }

    private fun convertIntCommissions(
        intCommissions: Sequence<IntCommissionDto>
    ): Sequence<IntCommission> {
        return intCommissions.map { it.toEntity() }
    }

    private fun saveIntCommissions(entities: Sequence<IntCommission>) {
        intCommissionRepository.saveAll(entities.asIterable())
    }

    private fun extractCountries(data: ApplicationDataDto): Sequence<CountryDto> {
        return data.countries.asSequence()
    }

    private fun convertCountries(countries: Sequence<CountryDto>): Sequence<Country> {
        return countries.toEntities()
    }

    private fun saveCountries(entities: Sequence<Country>) {
        countryRepository.saveAll(entities.asIterable())
    }

    private fun extractCommissions(data: ApplicationDataDto): Sequence<CommissionDto> {
        return data.commissions.asSequence()
    }

    private fun convertCommissions(commissions: Sequence<CommissionDto>): Sequence<Organisation> {
        return commissions.map { it.toEntity() }
    }

    private fun saveCommissions(entities: Sequence<Organisation>) {
        organisationRepository.saveAll(entities.asIterable())
    }

    private fun extractRegions(data: ApplicationDataDto): Sequence<RegionDto> {
        return data.regions.asSequence()
    }

    private fun convertRegions(regions: Sequence<RegionDto>): Sequence<Region> {
        return regions.map { it.toEntity() }
    }

    private fun saveRegions(entities: Sequence<Region>) {
        regionRepository.saveAll(entities.asIterable())
    }

    fun getFileText(filename: String): String { // TODO use stream
        return javaClass.classLoader.getResource(filename)?.readText()
            ?: throw IllegalArgumentException("File $filename not found")
    }
}
