package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class FractionPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var fractionRepository: FractionRepository

    @Autowired
    private lateinit var convocationRepository: ConvocationRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var fractionPositionRepository: FractionPositionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteConvocation_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()

        fractionPositionRepository.save(expected)
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM convocation WHERE id = ${expected.convocation?.id}")
            .executeUpdate()
        convocationRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deleteFraction_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()

        fractionPositionRepository.save(expected)
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM fraction WHERE id = ${expected.fraction?.id}")
            .executeUpdate()
        fractionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
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

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.person?.id}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

}
