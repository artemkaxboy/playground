package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToIntGroup
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToIntGroupRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class CountryToIntGroupTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intGroupRepository: IntGroupRepository

    @Autowired
    private lateinit var countryToIntGroupRepository: CountryToIntGroupRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteIntGroup_deletesAssociatedCountryToIntGroup() {
        val expected = makeCountryToIntGroup()

        countryToIntGroupRepository.save(expected)
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryToIntGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM int_group WHERE id = '${expected.intGroupId}'")
            .executeUpdate()
        countryRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        countryToIntGroupRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deleteCountry_deletesAssociatedCountryToIntGroup() {
        val expected = makeCountryToIntGroup()

        countryToIntGroupRepository.save(expected)
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryToIntGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${expected.countryId}'")
            .executeUpdate()
        countryRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        countryToIntGroupRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
