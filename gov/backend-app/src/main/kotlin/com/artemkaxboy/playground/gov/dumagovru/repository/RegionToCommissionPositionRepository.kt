package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.RegionToCommissionPosition
import org.springframework.data.jpa.repository.JpaRepository

interface RegionToCommissionPositionRepository :
    JpaRepository<RegionToCommissionPosition, RegionToCommissionPosition.IdClass>
