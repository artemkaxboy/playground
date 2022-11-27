package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class CommissionPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var commissionPositionRepository: CommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var commissionRepository: CommissionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun saveCommissionPosition_doesNotSaveAssociatedCommission() {
        val expected = makeCommissionPosition()

        Assertions.assertThatThrownBy { commissionPositionRepository.save(expected) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
            .hasCauseInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    @Transactional
    fun deleteCommission_deletesAssociatedCommissionPosition() {
        val expected = makeCommissionPosition()

        expected.commission?.let { commissionRepository.save(it) }
        expected.person?.let { personRepository.save(it) }
        commissionPositionRepository.save(expected)
        entityManager.createNativeQuery("DELETE FROM commission WHERE id = ${expected.commission?.id}")
            .executeUpdate()

        commissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedCommissionPosition() {
        val expected = makeCommissionPosition()

        expected.commission?.let { commissionRepository.save(it) }
        expected.person?.let { personRepository.save(it) }
        commissionPositionRepository.save(expected)
        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.person?.id}")
            .executeUpdate()
        
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
