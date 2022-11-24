package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToCountry
import org.springframework.data.jpa.repository.JpaRepository

interface CountryToCountryRepository : JpaRepository<CountryToCountry, CountryToCountry.IdClass>
