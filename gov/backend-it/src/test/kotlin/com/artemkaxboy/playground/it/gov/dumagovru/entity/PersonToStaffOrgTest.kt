package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makePersonToStaffOrg
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonToStaffOrgRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.StaffOrgRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class PersonToStaffOrgTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var staffOrgRepository: StaffOrgRepository

    @Autowired
    private lateinit var personToStaffOrgRepository: PersonToStaffOrgRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun deleteStaffOrg_deletesAssociatedPersonToStaffOrg() {
        val expected = makePersonToStaffOrg()

        personToStaffOrgRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personToStaffOrgRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        staffOrgRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager
            .createNativeQuery("DELETE FROM staff_org WHERE id = ${expected.staffOrgId}")
            .executeUpdate()
        staffOrgRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToStaffOrgRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }

    @Test
    @Transactional
    fun deletePerson_deletesAssociatedPersonToStaffOrg() {
        val expected = makePersonToStaffOrg()

        personToStaffOrgRepository.save(expected)
        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        personToStaffOrgRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
        staffOrgRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }

        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.personId}")
            .executeUpdate()
        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        personToStaffOrgRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
        staffOrgRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
    }
}
