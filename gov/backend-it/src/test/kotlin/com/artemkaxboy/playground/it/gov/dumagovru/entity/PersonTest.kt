package com.artemkaxboy.playground.it.gov.dumagovru.entity

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
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedCommissionPosition() {
        val expected = makeCommissionPosition()
        commissionPositionRepository.save(expected)

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()

        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()
        fractionPositionRepository.save(expected)

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()

        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
        convocationRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }
}
