package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToIntGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryToIntGroupRepositoryI : JpaRepository<CountryToIntGroup, CountryToIntGroup.IdClass>

@Repository
class CountryToIntGroupRepository(
    private val countryToIntGroupRepositoryI: CountryToIntGroupRepositoryI,
    private val countryRepository: CountryRepository,
    private val intGroupRepository: IntGroupRepository,
) : CountryToIntGroupRepositoryI by countryToIntGroupRepositoryI {

    override fun <S : CountryToIntGroup> save(countryToIntGroup: S): S {
        countryToIntGroup.country?.let { countryRepository.save(it) }
        countryToIntGroup.intGroup?.let { intGroupRepository.save(it) }
        return countryToIntGroupRepositoryI.save(countryToIntGroup)
    }
}
