package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.RegionToCommissionPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface RegionToCommissionPositionRepositoryI :
    JpaRepository<RegionToCommissionPosition, RegionToCommissionPosition.IdClass>

@Repository
class RegionToCommissionPositionRepository(
    private val regionToCommissionPositionRepositoryI: RegionToCommissionPositionRepositoryI,
    private val regionRepository: RegionRepository,
    private val commissionPositionRepository: CommissionPositionRepository,
) : RegionToCommissionPositionRepositoryI by regionToCommissionPositionRepositoryI {

    override fun <S : RegionToCommissionPosition> save(regionToCommissionPosition: S): S {
        regionToCommissionPosition.commissionPosition?.let { commissionPositionRepository.save(it) }
        regionToCommissionPosition.region?.let { regionRepository.save(it) }
        return regionToCommissionPositionRepositoryI.save(regionToCommissionPosition)
    }
}
