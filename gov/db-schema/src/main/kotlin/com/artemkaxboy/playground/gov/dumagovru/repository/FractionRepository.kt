package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Fraction
import org.springframework.data.jpa.repository.JpaRepository

interface FractionRepository : JpaRepository<Fraction, Long>
