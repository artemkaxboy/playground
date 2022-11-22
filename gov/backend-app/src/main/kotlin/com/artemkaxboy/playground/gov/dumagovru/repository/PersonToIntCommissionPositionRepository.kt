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

    override fun <S : PersonToIntCommissionPosition> save(personToIntCommissionPosition: S): S {
        personToIntCommissionPosition.person?.let { personRepository.save(it) }
        personToIntCommissionPosition.intCommissionPosition?.let { intCommissionPositionRepository.save(it) }
        return personToIntCommissionPositionRepositoryI.save(personToIntCommissionPosition)
    }
}
