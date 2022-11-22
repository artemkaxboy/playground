package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountryToIntCommission
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryToIntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class CountryToIntCommissionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intCommissionRepository: IntCommissionRepository

    @Autowired
    private lateinit var countryToIntCommissionRepository: CountryToIntCommissionRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteIntCommission_deletesAssociatedCountryToIntCommission() {
        val expected = makeCountryToIntCommission()
        countryToIntCommissionRepository.save(expected)
        countryToIntCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager
            .createNativeQuery("DELETE FROM int_commission WHERE id = ${expected.intCommissionId}")
            .executeUpdate()
        countryToIntCommissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intCommissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        countryRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
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
}
