package com.artemkaxboy.playground.gov.dumagovru

import com.artemkaxboy.playground.gov.dumagovru.dto.ApplicationDataDto
import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import com.artemkaxboy.playground.gov.dumagovru.entity.Fraction
import com.artemkaxboy.playground.gov.dumagovru.entity.toEntity
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import javax.transaction.Transactional

const val FILENAME = "duma-gov-ru/data.json"

@Component
class DbInitializer(
    private val commissionRepository: CommissionRepository,
    private val convocationRepository: ConvocationRepository,
    private val countryRepository: CountryRepository,
    private val fractionRepository: FractionRepository,
    private val intCommissionRepository: IntCommissionRepository,
    private val intGroupRepository: IntGroupRepository,
    private val personRepository: PersonRepository,
) {

    @Transactional
    @org.springframework.transaction.annotation.Transactional
    fun init() {
//        val pos = IntCommissionPosition(3, "title", intCommission = null, intCommissionId = 1, persons = emptySet())
//        val ent = IntCommission(1, "url", "lead", "title", emptySet(), setOf(pos))
//        intCommissionPositionRepository.save(pos)
//        intCommissionRepository.save(ent)
//    }
//
//    fun handleContextRefreshEvent() {

        val jsonString = getFileText(FILENAME)

        val json = Json { ignoreUnknownKeys = true }
        val data = json.decodeFromString<ApplicationDataDto>(jsonString)

        try {
            data.commissions.map { Commission.fromDto(it) }.let { commissionRepository.saveAll(it) }
            data.convocations.map { Convocation.fromDto(it) }.let { convocationRepository.saveAll(it) }
            data.countries.map { Country.fromDto(it) }.let { countryRepository.saveAll(it) }
            data.fractions.map { Fraction.fromDto(it) }.let { fractionRepository.saveAll(it) }
            data.intCommissions.map { it.toEntity() }
                .let { intCommissionRepository.saveAll(it) }
            data.intGroups.map { it.toEntity() }
                .let { intGroupRepository.saveAll(it) }
            data.persons.map { it.toEntity() }
                .let { personRepository.saveAll(it) }
            data.regions
        } finally {
            println("Done")
        }
    }

    fun getFileText(filename: String): String { // TODO use stream
        return javaClass.classLoader.getResource(filename)?.readText()
            ?: throw IllegalArgumentException("File $filename not found")
    }


}
