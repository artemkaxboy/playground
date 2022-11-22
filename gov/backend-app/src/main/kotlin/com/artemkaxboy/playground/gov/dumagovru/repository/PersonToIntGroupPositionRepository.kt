package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToIntGroupPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PersonToIntGroupPositionRepositoryI :
    JpaRepository<PersonToIntGroupPosition, PersonToIntGroupPosition.IdClass>

@Repository
class PersonToIntGroupPositionRepository(
    private val personToIntGroupPositionRepositoryI: PersonToIntGroupPositionRepositoryI,
    private val personRepository: PersonRepository,
    private val intGroupPositionRepository: IntGroupPositionRepository,
) : PersonToIntGroupPositionRepositoryI by personToIntGroupPositionRepositoryI {

    override fun <S : PersonToIntGroupPosition> save(personToIntGroupPosition: S): S {
        personToIntGroupPosition.person?.let { personRepository.save(it) }
        personToIntGroupPosition.intGroupPosition?.let { intGroupPositionRepository.save(it) }
        return personToIntGroupPositionRepositoryI.save(personToIntGroupPosition)
    }
}
