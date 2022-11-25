package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import org.springframework.data.jpa.repository.JpaRepository

interface CommissionPositionRepository : JpaRepository<CommissionPosition, CommissionPosition.IdClass>
