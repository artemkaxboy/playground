package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeIntGroup
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException

internal class IntGroupTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intGroupRepository: IntGroupRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun saveIntGroup_doesNotSaveCountry() {

        val intGroup = makeIntGroup()
        assertThatThrownBy { intGroupRepository.save(intGroup) }
            .isInstanceOf(JpaObjectRetrievalFailureException::class.java)
            .hasCauseInstanceOf(EntityNotFoundException::class.java)
    }

    @Test
    @Transactional
    fun deleteIntGroup_doesNotDeleteAssociatedCountry() {
        val expected = makeIntGroup()
        countryRepository.save(expected.countries.first())
        intGroupRepository.save(expected)

        entityManager.createNativeQuery("DELETE FROM int_group WHERE id = '${expected.id}'").executeUpdate()

        assertThat(intGroupRepository.findAll()).isEmpty()
        assertThat(countryRepository.findAll())
            .containsExactlyInAnyOrderElementsOf(expected.countries)
    }

    @Test
    @Transactional
    fun deleteCountry_doesNotDeleteAssociatedIntGroup() {
        val expected = makeIntGroup()
        countryRepository.save(expected.countries.first())
        intGroupRepository.save(expected)
        val unexpectedCountry = expected.countries.first()

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${unexpectedCountry.id}'")
            .executeUpdate()

        assertThat(countryRepository.findAll()).isEmpty()
        assertThat(intGroupRepository.findAll())
            .containsExactlyInAnyOrder(expected)
    }
}
