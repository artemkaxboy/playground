package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class CommissionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var commissionPositionRepository: CommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var commissionRepository: CommissionRepository

    @AfterEach
    fun tearDown() {
        personRepository.deleteAll()
        commissionRepository.deleteAll()
        commissionPositionRepository.deleteAll()
    }

    @Test
    fun deleteCommission_deletesAssociatedCommissionPositionNotPerson() {
        val expected = makeCommissionPosition()
        saveCommissionPositionWithAssociated(expected)

        commissionRepository.deleteById(expected.commissionId!!)

        commissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    private fun saveCommissionPositionWithAssociated(commissionPosition: CommissionPosition) {
        personRepository.save(commissionPosition.person!!)
        commissionRepository.save(commissionPosition.commission!!)
        commissionPositionRepository.save(commissionPosition)
    }
}
