package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.FractionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.ConvocationRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class ConvocationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var fractionRepository: FractionRepository

    @Autowired
    private lateinit var convocationRepository: ConvocationRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var fractionPositionRepository: FractionPositionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteConvocation_deletesAssociatedFractionPosition() {
        val expected = makeFractionPosition()
        saveFractionPositionWithAssociated(expected)

        entityManager.createNativeQuery("DELETE FROM convocation WHERE id = ${expected.convocationId}")
            .executeUpdate()

        convocationRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
        fractionRepository.findAll().let { Assertions.assertThat(it).isNotEmpty }
    }

    private fun saveFractionPositionWithAssociated(fractionPosition: FractionPosition) {
        fractionRepository.save(fractionPosition.fraction!!)
        convocationRepository.save(fractionPosition.convocation!!)
        personRepository.save(fractionPosition.person!!)
        fractionPositionRepository.save(fractionPosition)
    }
}
