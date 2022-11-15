package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.FractionPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface FractionPositionRepositoryI : JpaRepository<FractionPosition, FractionPosition.IdClass>

@Repository
class FractionPositionRepository(
    private val fractionPositionRepositoryI: FractionPositionRepositoryI,
    private val fractionRepository: FractionRepository,
    private val convocationRepository: ConvocationRepository,
    private val personRepository: PersonRepository,
) : FractionPositionRepositoryI by fractionPositionRepositoryI {

    fun save(fractionPosition: FractionPosition) {
        fractionPosition.fraction?.let { fractionRepository.save(it) }
        fractionPosition.convocation?.let { convocationRepository.save(it) }
        fractionPosition.person?.let { personRepository.save(it) }
        fractionPositionRepositoryI.save(fractionPosition)
    }
}
