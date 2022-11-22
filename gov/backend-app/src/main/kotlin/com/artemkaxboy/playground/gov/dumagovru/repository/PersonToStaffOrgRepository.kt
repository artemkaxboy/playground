package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToStaffOrg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface PersonToStaffOrgRepositoryI :
    JpaRepository<PersonToStaffOrg, PersonToStaffOrg.IdClass>

@Repository
class PersonToStaffOrgRepository(
    private val personToStaffOrgRepositoryI: PersonToStaffOrgRepositoryI,
    private val personRepository: PersonRepository,
    private val staffOrgRepository: StaffOrgRepository,
) : PersonToStaffOrgRepositoryI by personToStaffOrgRepositoryI {

    override fun <S : PersonToStaffOrg> save(personToStaffOrg: S): S {
        personToStaffOrg.person?.let { personRepository.save(it) }
        personToStaffOrg.staffOrg?.let { staffOrgRepository.save(it) }
        return personToStaffOrgRepositoryI.save(personToStaffOrg)
    }
}
