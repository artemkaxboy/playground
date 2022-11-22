package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryRepositoryI : JpaRepository<Country, String>

@Repository
class CountryRepository(private val countryRepositoryI: CountryRepositoryI) :
    CountryRepositoryI by countryRepositoryI {

    fun save(country: Country) {
        countryRepositoryI.save(country)
    }

    fun saveAllTwoSteps(countries: List<Country>) {
//        saveAll(countries.map { it.copy(associatedTo = emptySet()) })
        saveAll(countries)
    }

}