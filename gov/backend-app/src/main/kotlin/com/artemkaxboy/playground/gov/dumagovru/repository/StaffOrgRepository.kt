package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.StaffOrg
import org.springframework.data.jpa.repository.JpaRepository

interface StaffOrgRepository : JpaRepository<StaffOrg, Long>
