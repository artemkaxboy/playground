package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makePersonToIntCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonToIntCommissionPositionRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class PersonToIntCommissionPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intCommissionPositionRepository: IntCommissionPositionRepository

    @Autowired
    private lateinit var personToIntCommissionPositionRepository: PersonToIntCommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteIntCommissionPosition_deletesAssociatedPersonToIntCommissionPosition() {
        val expected = makePersonToIntCommissionPosition()
        personToIntCommissionPositionRepository.save(expected)

        entityManager
            .createNativeQuery("DELETE FROM int_commission_position WHERE id = ${expected.intCommissionPositionId}")
            .executeUpdate()

        intCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedPersonToIntCommissionPosition() {
        val expected = makePersonToIntCommissionPosition()

        personToIntCommissionPositionRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
