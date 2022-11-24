package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.PersonToStaffOrg
import org.springframework.data.jpa.repository.JpaRepository

interface PersonToStaffOrgRepository : JpaRepository<PersonToStaffOrg, PersonToStaffOrg.IdClass>
