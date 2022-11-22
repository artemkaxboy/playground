package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makePersonToIntCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonToIntCommissionPositionRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

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

    @Autowired
    private lateinit var personToIntCommissionPositionRepository: PersonToIntCommissionPositionRepository

    @Autowired
    private lateinit var intCommissionPositionRepository: IntCommissionPositionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedCommissionPosition() {
        val expected = makeCommissionPosition()

        commissionPositionRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        commissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()

        fractionPositionRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedPersonToIntCommissionPosition() {
        val expected = makePersonToIntCommissionPosition()

        personToIntCommissionPositionRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
