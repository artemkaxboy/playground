package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.FractionPosition
import org.springframework.data.jpa.repository.JpaRepository

interface FractionPositionRepository : JpaRepository<FractionPosition, FractionPosition.IdClass>
