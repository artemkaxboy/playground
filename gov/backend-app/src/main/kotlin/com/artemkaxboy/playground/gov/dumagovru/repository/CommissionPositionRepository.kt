package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface CommissionPositionRepositoryI : JpaRepository<CommissionPosition, String>

@Repository
class CommissionPositionRepository(
    private val commissionPositionRepositoryI: CommissionPositionRepositoryI,
    private val commissionRepository: CommissionRepository,
    private val personRepository: PersonRepository,
) : CommissionPositionRepositoryI by commissionPositionRepositoryI {

    fun save(commissionPosition: CommissionPosition) {
        commissionPosition.commission?.let { commissionRepository.save(it) }
        commissionPosition.person?.let { personRepository.save(it) }
        commissionPositionRepositoryI.save(commissionPosition)
    }
}
