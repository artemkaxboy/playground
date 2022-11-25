package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToIntGroupPosition
import org.springframework.data.jpa.repository.JpaRepository

interface PersonToIntGroupPositionRepository : JpaRepository<PersonToIntGroupPosition, PersonToIntGroupPosition.IdClass>
