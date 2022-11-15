package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.RegionToCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeRegionToCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.CommissionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionToCommissionPositionRepository
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
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteRegion_deletesAssociatedRegionToCommissionPosition() {
        val expected = makeRegionToCommissionPosition()
        saveRegionToCommissionPositionWithAssociated(expected)

        entityManager.createNativeQuery("DELETE FROM region WHERE id = ${expected.regionId}")
            .executeUpdate()

        regionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        regionToCommissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        commissionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
    }

    private fun saveRegionToCommissionPositionWithAssociated(regionToCommissionPosition: RegionToCommissionPosition) {
        regionToCommissionPosition.commissionPosition!!.let { commissionPositionRepository.save(it) }
        regionToCommissionPosition.region!!.let { regionRepository.save(it) }
        regionToCommissionPosition.let { regionToCommissionPositionRepository.save(it) }
    }
}
