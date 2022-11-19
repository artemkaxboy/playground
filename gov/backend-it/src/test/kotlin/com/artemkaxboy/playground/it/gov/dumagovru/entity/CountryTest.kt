package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToCounty
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToIntCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToIntGroup
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToCountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToIntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToIntGroupRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class CountryTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var countryToCountryRepository: CountryToCountryRepository

    @Autowired
    private lateinit var countryToIntCommissionRepository: CountryToIntCommissionRepository

    @Autowired
    private lateinit var countryToIntGroupRepository: CountryToIntGroupRepository

    @Autowired
    private lateinit var intCommissionRepository: IntCommissionRepository

    @Autowired
    private lateinit var intGroupRepository: IntGroupRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteCountry_deletesAssociatedCountryToCountryFrom() {
        val expected = makeCountryToCounty()

        countryToCountryRepository.save(expected)
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(2) }
        countryToCountryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${expected.fromCountyId}'")
            .executeUpdate()
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryToCountryRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
    }

    @Test
    @Transactional
    fun deleteCountry_deletesAssociatedCountryToCountryTo() {
        val expected = makeCountryToCounty()

        countryToCountryRepository.save(expected)
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(2) }
        countryToCountryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${expected.toCountyId}'")
            .executeUpdate()
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryToCountryRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
    }

    @Test
    @Transactional
    fun deleteCountry_deletesAssociatedCountryToIntCommission() {
        val expected = makeCountryToIntCommission()

        countryToIntCommissionRepository.save(expected)
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryToIntCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${expected.countryId}'")
            .executeUpdate()
        countryRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        countryToIntCommissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
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
