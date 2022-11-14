package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class CommissionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var commissionPositionRepository: CommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var commissionRepository: CommissionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteCommission_deletesAssociatedCommissionPositionNotPerson() {
        val expected = makeCommissionPosition()
        saveCommissionPositionWithAssociated(expected)

        entityManager.createNativeQuery("DELETE FROM commission WHERE id = ${expected.commissionId}")
            .executeUpdate()

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
