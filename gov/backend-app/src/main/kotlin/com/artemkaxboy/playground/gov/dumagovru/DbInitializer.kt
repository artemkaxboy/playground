package com.artemkaxboy.playground.gov.dumagovru

import com.artemkaxboy.playground.gov.dumagovru.dto.ApplicationDataDto
import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto
import com.artemkaxboy.playground.gov.dumagovru.dto.FractionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.IntCommissionDto
import com.artemkaxboy.playground.gov.dumagovru.dto.IntGroupDto
import com.artemkaxboy.playground.gov.dumagovru.dto.PersonDto
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

        savePeople(extractPeople(data))
        saveFractions(extractFractions(data))
        saveIntGroups(extractIntGroups(data))
        saveIntCommissions(extractIntCommissions(data))
        saveCountries(extractCountries(data))

        logger.info { "Database initialized" }
    }

    private fun extractPeople(data: ApplicationDataDto): Set<PersonDto> {
        return data.persons
    }

    private fun extractFractions(data: ApplicationDataDto): List<FractionDto> {
        return data.fractions.filter { it.type == "fraction" }
    }

    private fun extractIntGroups(data: ApplicationDataDto): Set<IntGroupDto> {
        return data.intGroups
    }

    private fun extractIntCommissions(data: ApplicationDataDto): Set<IntCommissionDto> {
        return data.intCommissions
    }

    private fun extractCountries(data: ApplicationDataDto): Set<CountryDto> {
        return data.countries
    }

    private fun savePeople(people: Collection<PersonDto>) {
        personRepository.saveAll(people.map { it.toEntity() })
    }

    private fun saveFractions(fractions: Collection<FractionDto>) {
        fractionRepository.saveAll(fractions.map { it.toEntity() })
    }

    private fun saveIntGroups(intGroups: Collection<IntGroupDto>) {
        intGroupRepository.saveAll(intGroups.map { it.toEntity() })
    }

    private fun saveIntCommissions(intCommissions: Collection<IntCommissionDto>) {
        intCommissionRepository.saveAll(intCommissions.map { it.toEntity() })
    }

    private fun saveCountries(countries: Collection<CountryDto>) {
        countryRepository.saveAll(countries.map { it.toEntity() })
    }

    fun getFileText(filename: String): String { // TODO use stream
        return javaClass.classLoader.getResource(filename)?.readText()
            ?: throw IllegalArgumentException("File $filename not found")
    }
}
