package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToIntGroup
import org.springframework.data.jpa.repository.JpaRepository

interface CountryToIntGroupRepository : JpaRepository<CountryToIntGroup, CountryToIntGroup.IdClass>
