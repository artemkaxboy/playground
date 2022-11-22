package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.IntGroupPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface IntGroupPositionRepositoryI : JpaRepository<IntGroupPosition, Long>

@Repository
class IntGroupPositionRepository(
    private val intGroupPositionRepositoryI: IntGroupPositionRepositoryI,
    private val intGroupRepository: IntGroupRepository,
) : IntGroupPositionRepositoryI by intGroupPositionRepositoryI {

     override fun <S : IntGroupPosition> save(intGroupPosition: S): S {
         intGroupPosition.intGroup?.let { intGroupRepository.save(it) }
         return intGroupPositionRepositoryI.save(intGroupPosition)
     }
}
