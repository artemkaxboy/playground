package com.artemkaxboy.playground.gov.dumagovru.repository

import com.artemkaxboy.playground.gov.dumagovru.entity.Organisation
import org.springframework.data.jpa.repository.JpaRepository

interface OrganisationRepository : JpaRepository<Organisation, Long>
