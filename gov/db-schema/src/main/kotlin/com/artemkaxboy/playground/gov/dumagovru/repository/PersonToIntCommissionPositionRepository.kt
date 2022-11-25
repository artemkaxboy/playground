package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToIntCommissionPosition
import org.springframework.data.jpa.repository.JpaRepository

interface PersonToIntCommissionPositionRepository :
    JpaRepository<PersonToIntCommissionPosition, PersonToIntCommissionPosition.IdClass>
