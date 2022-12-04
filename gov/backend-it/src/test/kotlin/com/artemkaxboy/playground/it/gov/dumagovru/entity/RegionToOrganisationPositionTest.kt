package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makeRegionToFractionPosition
import com.artemkaxboy.playground.gov.dumagovru.repository.FractionPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.RegionToFractionPositionRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class RegionToOrganisationPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var fractionPositionRepository: FractionPositionRepository

    @Autowired
    private lateinit var regionToFractionPositionRepository: RegionToFractionPositionRepository

    @Autowired
    private lateinit var regionRepository: RegionRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteFractionPosition_deletesAssociatedRegionToFractionPosition() {
        val expected = makeRegionToFractionPosition()

        regionToFractionPositionRepository.save(expected)
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        regionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery(
            "DELETE FROM fraction_position " +
                    "WHERE person_id = ${expected.fractionPosition?.person?.id} " +
                    "AND fraction_id = ${expected.fractionPosition?.fraction?.id} " +
                    "AND convocation_id = ${expected.fractionPosition?.convocation?.id}"
        )
            .executeUpdate()
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        regionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
    }

    @Test
    @Transactional
    fun deleteRegion_deletesAssociatedRegionToFractionPosition() {
        val expected = makeRegionToFractionPosition()

        regionToFractionPositionRepository.save(expected)
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        regionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM region WHERE id = ${expected.region?.id}")
            .executeUpdate()
        regionToFractionPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        regionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        fractionPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
