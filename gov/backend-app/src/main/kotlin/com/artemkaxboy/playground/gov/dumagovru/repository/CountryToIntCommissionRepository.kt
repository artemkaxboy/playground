package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToIntCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToIntGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CountryToIntCommissionRepositoryI : JpaRepository<CountryToIntCommission, CountryToIntCommission.IdClass>

@Repository
class CountryToIntCommissionRepository(
    private val countryToIntCommissionRepositoryI: CountryToIntCommissionRepositoryI,
    private val countryRepository: CountryRepository,
    private val intCommissionRepository: IntCommissionRepository,
) :
    CountryToIntCommissionRepositoryI by countryToIntCommissionRepositoryI {

    override fun <S : CountryToIntCommission> save(countryToIntCommission: S): S {
        countryToIntCommission.country?.let { countryRepository.save(it) }
        countryToIntCommission.intCommission?.let { intCommissionRepository.save(it) }
        return countryToIntCommissionRepositoryI.save(countryToIntCommission)
    }
}
