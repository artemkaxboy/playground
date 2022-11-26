package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CountryRepository : JpaRepository<Country, String> {

    @EntityGraph(attributePaths = ["associatedCountries"])
    @Query("SELECT c FROM Country c")
    fun findAllWithAssociatedCountries(): List<Country>

    @EntityGraph(attributePaths = ["associatedCountries"])
    @Query("SELECT c FROM Country c WHERE c.id = :id")
    fun findByIdWithAssociatedCountries(id: String): Country?
}
