package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToCountry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryToCountryRepositoryI : JpaRepository<CountryToCountry, CountryToCountry.IdClass>

@Repository
class CountryToCountryRepository(
    private val countryToCountryRepositoryI: CountryToCountryRepositoryI,
    private val countryRepository: CountryRepository,
) : CountryToCountryRepositoryI by countryToCountryRepositoryI {

    override fun <S : CountryToCountry> save(countryToCountry: S): S {
        countryToCountry.let { listOf(it.fromCountry, it.toCountry) }.let { countryRepository.saveAll(it) }
        return countryToCountryRepositoryI.save(countryToCountry)
    }
}
