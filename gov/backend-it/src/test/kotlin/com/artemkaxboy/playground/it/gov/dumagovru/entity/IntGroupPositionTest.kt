package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeIntGroupPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class IntGroupPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intGroupRepository: IntGroupRepository

    @Autowired
    private lateinit var intGroupPositionRepository: IntGroupPositionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteIntGroup_deletesAssociatedIntGroupPosition() {
        val expected = makeIntGroupPosition()

        intGroupPositionRepository.save(expected)
        intGroupRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM int_group WHERE id = '${expected.intGroupId}'")
            .executeUpdate()
        intGroupRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
    }
}
