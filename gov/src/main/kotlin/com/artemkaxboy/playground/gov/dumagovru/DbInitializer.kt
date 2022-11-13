package com.artemkaxboy.playground.gov.dumagovru

import com.artemkaxboy.playground.gov.dumagovru.dto.ApplicationDataDto
import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

const val FILENAME = "duma-gov-ru/data.json"

@Component
class DbInitializer(
    private val commissionRepository: CommissionRepository,
    private val commissionPositionRepository: CommissionPositionRepository,
    private val convocationRepository: ConvocationRepository,
    private val countryRepository: CountryRepository,
    private val fractionRepository: FractionRepository,
    private val intGroupRepository: IntGroupRepository,
    private val personRepository: PersonRepository,
    private val regionRepository: RegionRepository,
) {

    @Transactional
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

//        data.regions.map { it.toEntity() } // must be before fractions
//            .let { regionRepository.saveAll(it) }
//        data.countries.map { it.toEntity() }.let { countryRepository.saveAllTwoSteps(it) }
//
//        data.commissions.map { com.artemkaxboy.backup.gov.dumagovru.entity.toEntity() }
//            .let { commissionRepository.saveAll(it) }
//        data.convocations.map { it.toEntity() }.let { convocationRepository.saveAll(it) }
//        data.fractions.map { it.toEntity() }.let { fractionRepository.saveAll(it) }
//        data.intCommissions.map { it.toEntity() }
//            .let { intCommissionRepository.saveAll(it) }
//        data.intGroups.map { it.toEntity() }
//            .let { intGroupRepository.saveAll(it) }
//        data.persons.map { it.toEntity() }
//            .let { personRepository.saveAll(it) }


        Commission(1, "commission")
            .let { commissionRepository.save(it) }
        Person(1, "Artem", "E", "Kolin", "Artem E Kolin")
            .let { personRepository.save(it) }
        CommissionPosition(personId = 1, commissionId = 1, positionText = "position text")
            .let { commissionPositionRepository.save(it) }


    }

    fun getFileText(filename: String): String { // TODO use stream
        return javaClass.classLoader.getResource(filename)?.readText()
            ?: throw IllegalArgumentException("File $filename not found")
    }

    @Transactional
    fun test() {
//        val all = personRepository.findAll().filter { it.fractionPositions.size > 1 }
//        all.forEach { println(it) }
    }
}
