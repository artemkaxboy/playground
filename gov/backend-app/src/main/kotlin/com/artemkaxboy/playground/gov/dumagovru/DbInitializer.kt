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
import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import com.artemkaxboy.playground.gov.dumagovru.entity.Fraction
import com.artemkaxboy.playground.gov.dumagovru.entity.IntCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.IntGroup
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import com.artemkaxboy.playground.gov.dumagovru.entity.Region
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
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
    private val commissionRepository: CommissionRepository,
    private val commissionPositionRepository: CommissionPositionRepository,
    private val convocationRepository: ConvocationRepository,
    private val countryRepository: CountryRepository,
    private val fractionRepository: FractionRepository,
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
        saveCommissions(convertCommissions(extractCommissions(data))) // need: nothing
        saveFractions(convertFractions(extractFractions(data))) // need: nothing

        val peopleEntities = convertPeople(extractPeople(data))
        savePeople(peopleEntities) // need: commission fraction, inner: staffOrg

        saveCountries(convertCountries(extractCountries(data))) // need: nothing

        val intGroupEntities = convertIntGroups(extractIntGroups(data), peopleEntities)
        saveIntGroups(intGroupEntities) // need: countries

        val intCommissionEntities = convertIntCommissions(extractIntCommissions(data))
        saveIntCommissions(intCommissionEntities)

        saveRegions(convertRegions(extractRegions(data)))
        saveConvocations(convertConvocations(extractConvocations(data)))

        logger.info { "Database initialized" }
    }

    private fun extractPeople(data: ApplicationDataDto): Sequence<PersonDto> {
        return data.persons.asSequence()
    }

    private fun convertPeople(peopleDtos: Sequence<PersonDto>): Sequence<Person> {
        return peopleDtos.map { it.toEntity() }
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

    private fun extractFractions(data: ApplicationDataDto): Sequence<FractionDto> {
        return data.fractions.asSequence().filter { it.type == "fraction" }
    }

    private fun convertFractions(fractions: Sequence<FractionDto>): Sequence<Fraction> {
        return fractions.map { it.toEntity() }
    }

    private fun saveFractions(fractions: Sequence<Fraction>) {
        fractionRepository.saveAll(fractions.asIterable())
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

    private fun convertCommissions(commissions: Sequence<CommissionDto>): Sequence<Commission> {
        return commissions.map { it.toEntity() }
    }

    private fun saveCommissions(entities: Sequence<Commission>) {
        commissionRepository.saveAll(entities.asIterable())
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
