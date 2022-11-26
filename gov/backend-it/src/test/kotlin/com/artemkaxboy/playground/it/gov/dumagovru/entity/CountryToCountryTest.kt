package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCountry
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


internal class CountryToCountryTest : AbstractIntegrationTest() {

    private val countryA = makeCountry(id = "A", title = "country A")
    private val countryB = makeCountry(id = "B", title = "country B")

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun saveCounty_whenCountrySelfAssociated() {
        val country = countryA.copy()
        country.associatedCountries.add(country)

        countryRepository.save(country)

        val actual = countryRepository.findAllWithAssociatedCountries()
        assertThat(actual).containsExactlyInAnyOrder(country)
        val actualAssociated = actual.first().associatedCountries
        assertThat(actualAssociated).containsExactlyInAnyOrder(country)
    }

    @Test
    @Transactional
    fun saveCountry_whenHaveAssociatedCountries() {
        val countryA = this.countryA.copy()
        val countryB = this.countryB.copy()
        countryA.associatedCountries.add(countryB)

        countryRepository.save(countryA)

        val actual = countryRepository.findAllWithAssociatedCountries()
        assertThat(actual).containsExactlyInAnyOrder(countryA, countryB)
    }

    @Test
    @Transactional
    fun deleteCountry_whenDeleteSelfAssociated() {
        val country = this.countryA.copy()
        country.associatedCountries.add(country)
        countryRepository.save(country)

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${country.id}'").executeUpdate()

        val actual = countryRepository.findAllWithAssociatedCountries()
        assertThat(actual).isEmpty()
    }

    @Test
    @Transactional
    fun deleteCountry_whenDeleteMain() {
        val countryA = this.countryA.copy()
        val countryB = this.countryB.copy()
        countryA.associatedCountries.add(countryB)
        countryRepository.save(countryA)

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${countryA.id}'").executeUpdate()

        val actual = countryRepository.findAllWithAssociatedCountries()
        assertThat(actual).containsExactly(countryB)
        val actualAssociated = actual.first().associatedCountries
        assertThat(actualAssociated).isEmpty()
    }

    @Test
    @Transactional
    fun deleteCountry_whenDeleteAssociated() {
        val countryA = this.countryA.copy()
        val countryB = this.countryB.copy()
        countryA.associatedCountries.add(countryB)
        countryRepository.save(countryA)

        entityManager.createNativeQuery("DELETE FROM country WHERE id = '${countryB.id}'").executeUpdate()
        entityManager.clear()

        val actual = countryRepository.findAllWithAssociatedCountries()
        assertThat(actual).containsExactly(countryA)
        val actualAssociated = actual.first().associatedCountries
        assertThat(actualAssociated).isEmpty()
    }

    @Test
    @Transactional
    fun save_whenAssociatedUpdated_notUpdateAssociatedInDb() {
        val countryA = this.countryA.copy()
        val countryB = this.countryB.copy()
        countryA.associatedCountries.add(countryB)
        countryRepository.save(countryA)

        val notExpected = "new title"
        val updatedA = this.countryA.copy()
        val updatedB = this.countryB.copy(title = notExpected)
        updatedA.associatedCountries.add(updatedB)
        countryRepository.save(updatedA)

        val actual = countryRepository.findByIdWithAssociatedCountries(countryB.id!!)
        assertThat(actual?.title)
            .isNotNull
            .isNotEqualTo(notExpected)
    }
}
