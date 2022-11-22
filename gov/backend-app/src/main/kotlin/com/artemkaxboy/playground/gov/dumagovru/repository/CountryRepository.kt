package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryRepositoryI : JpaRepository<Country, String>

@Repository
class CountryRepository(
    private val countryRepositoryI: CountryRepositoryI,
) : CountryRepositoryI by countryRepositoryI {

    override fun <S : Country> save(country: S): S {
        return countryRepositoryI.save(country)
    }
}
