package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryRepositoryI : JpaRepository<Country, String>

@Repository
class CountryRepository(countryRepositoryI: CountryRepositoryI) : CountryRepositoryI by countryRepositoryI {

    fun saveAllTwoSteps(countries: List<Country>) {
        saveAll(countries.map { it.copy(associated = emptySet()) })
        saveAll(countries)
    }

}
