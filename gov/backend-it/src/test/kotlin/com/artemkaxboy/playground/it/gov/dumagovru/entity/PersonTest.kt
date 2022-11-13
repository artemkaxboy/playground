package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.FractionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PersonTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var commissionPositionRepository: CommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var commissionRepository: CommissionRepository

    @Autowired
    private lateinit var fractionPositionRepository: FractionPositionRepository

    @Autowired
    private lateinit var fractionRepository: FractionRepository

    @Autowired
    private lateinit var convocationRepository: ConvocationRepository

    @AfterEach
    fun tearDown() {
        personRepository.deleteAll()
        commissionRepository.deleteAll()
        commissionPositionRepository.deleteAll()
        fractionPositionRepository.deleteAll()
        fractionRepository.deleteAll()
        convocationRepository.deleteAll()
    }

    @Test
    fun deletePerson_deletesAssociatedCommissionPositionNotCommission() {
        val expected = makeCommissionPosition()
        saveUserWithAssociatedCommissionPosition(expected)

        personRepository.deleteById(expected.personId!!)

        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    @Test
    fun deletePerson_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()
        saveFractionPositionWithAssociated(expected)

        personRepository.deleteById(expected.personId!!)

        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
        convocationRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    private fun saveUserWithAssociatedCommissionPosition(commissionPosition: CommissionPosition) {
        personRepository.save(commissionPosition.person!!)
        commissionRepository.save(commissionPosition.commission!!)
        commissionPositionRepository.save(commissionPosition)
    }

    private fun saveFractionPositionWithAssociated(fractionPosition: FractionPosition) {
        fractionRepository.save(fractionPosition.fraction!!)
        convocationRepository.save(fractionPosition.convocation!!)
        personRepository.save(fractionPosition.person!!)
        fractionPositionRepository.save(fractionPosition)
    }

}
