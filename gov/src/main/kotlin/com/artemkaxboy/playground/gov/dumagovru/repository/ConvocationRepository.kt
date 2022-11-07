package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import org.springframework.data.jpa.repository.JpaRepository

interface ConvocationRepository : JpaRepository<Convocation, String>
