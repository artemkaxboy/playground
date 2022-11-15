package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToCountry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryToCountryRepositoryI : JpaRepository<CountryToCountry, CountryToCountry>

@Repository
class CountryToCountryRepository(
    private val countryToCountryRepositoryI: CountryToCountryRepositoryI,
    private val countryRepository: CountryRepository,
) :
    CountryToCountryRepositoryI by countryToCountryRepositoryI {

    fun save(countryToCountry: CountryToCountry) {
        countryToCountry.let { listOf(it.fromCountry, it.toCountry) }.let { countryRepository.saveAll(it) }
        countryToCountryRepositoryI.save(countryToCountry)
    }
}
