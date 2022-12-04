package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.OrganisationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class OrganisationPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var organisationRepository: OrganisationRepository

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
        organisationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM convocation WHERE id = ${expected.convocation?.id}")
            .executeUpdate()
        convocationRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        organisationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deleteFraction_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()

        fractionPositionRepository.save(expected)
        organisationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM fraction WHERE id = ${expected.fraction?.id}")
            .executeUpdate()
        organisationRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
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
        organisationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.person?.id}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        organisationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        convocationRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

}
