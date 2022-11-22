package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.RegionToFractionPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface RegionToFractionPositionRepositoryI :
    JpaRepository<RegionToFractionPosition, RegionToFractionPosition.IdClass>

@Repository
class RegionToFractionPositionRepository(
    private val regionToFractionPositionRepositoryI: RegionToFractionPositionRepositoryI,
    private val regionRepository: RegionRepository,
    private val fractionPositionRepository: FractionPositionRepository,
) : RegionToFractionPositionRepositoryI by regionToFractionPositionRepositoryI {

    fun save(regionToFractionPosition: RegionToFractionPosition) {
        regionToFractionPosition.fractionPosition?.let { fractionPositionRepository.save(it) }
        regionToFractionPosition.region?.let { regionRepository.save(it) }
        regionToFractionPositionRepositoryI.save(regionToFractionPosition)
    }
}
