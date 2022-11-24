package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.RegionToFractionPosition
import org.springframework.data.jpa.repository.JpaRepository

interface RegionToFractionPositionRepository : JpaRepository<RegionToFractionPosition, RegionToFractionPosition.IdClass>
