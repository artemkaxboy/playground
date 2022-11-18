package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToIntCommissionPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PersonToIntCommissionPositionRepositoryI :
    JpaRepository<PersonToIntCommissionPosition, PersonToIntCommissionPosition.IdClass>

@Repository
class PersonToIntCommissionPositionRepository(
    private val personToIntCommissionPositionRepositoryI: PersonToIntCommissionPositionRepositoryI,
    private val personRepository: PersonRepository,
    private val intCommissionPositionRepository: IntCommissionPositionRepository,
) : PersonToIntCommissionPositionRepositoryI by personToIntCommissionPositionRepositoryI {

    fun save(personToIntCommissionPosition: PersonToIntCommissionPosition) {
        personToIntCommissionPosition.person?.let { personRepository.save(it) }
        personToIntCommissionPosition.intCommissionPosition?.let { intCommissionPositionRepository.save(it) }
        personToIntCommissionPositionRepositoryI.save(personToIntCommissionPosition)
    }
}
