package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeRegionToCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeRegionToFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionToCommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionToFractionPositionRepository
import com.artemkaxboy.playground.it.gov.dumagovru.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class RegionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var regionRepository: RegionRepository

    @Autowired
    private lateinit var regionToCommissionPositionRepository: RegionToCommissionPositionRepository

    @Autowired
    private lateinit var commissionPositionRepository: CommissionPositionRepository

    @Autowired
    private lateinit var regionToFractionPositionRepository: RegionToFractionPositionRepository

    @Autowired
    private lateinit var fractionPositionRepository: FractionPositionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteRegion_deletesAssociatedRegionToCommissionPosition() {
        val expected = makeRegionToCommissionPosition()

        regionToCommissionPositionRepository.save(expected)
        regionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        regionToCommissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM region WHERE id = ${expected.regionId}")
            .executeUpdate()
        regionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        regionToCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deleteRegion_deletesAssociatedRegionToFractionPosition() {
        val expected = makeRegionToFractionPosition()

        regionToFractionPositionRepository.save(expected)
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        regionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM region WHERE id = ${expected.regionId}")
            .executeUpdate()
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        regionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) } // todo ???
    }
}