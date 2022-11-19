package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makePersonToIntCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntCommissionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonToIntCommissionPositionRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class IntCommissionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intCommissionRepository: IntCommissionRepository

    @Autowired
    private lateinit var personToIntCommissionPositionRepository: PersonToIntCommissionPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedPersonToIntCommissionPosition() {
        val expected = makePersonToIntCommissionPosition()
        personToIntCommissionPositionRepository.save(expected)
        intCommissionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager
            .createNativeQuery("DELETE FROM int_commission WHERE id = ${expected.intCo}")
            .executeUpdate()
        intCommissionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToIntCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
