package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToCounty
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToIntCommission
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToCountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToIntCommissionRepository
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
    private lateinit var intCommissionRepository: CountryToIntCommissionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteCountry_deletesAssociatedCountryToCountryFrom() {
        val expected = makeCountryToCounty()

        countryToCountryRepository.save(expected)

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

        countryRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
        countryToIntCommissionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
        intCommissionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }
}
