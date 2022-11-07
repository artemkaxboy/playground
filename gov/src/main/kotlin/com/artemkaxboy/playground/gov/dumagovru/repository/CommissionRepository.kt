package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
import org.springframework.data.jpa.repository.JpaRepository

interface CommissionRepository : JpaRepository<Commission, String>
