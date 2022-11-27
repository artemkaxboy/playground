package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.entity.makePerson
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

internal class StaffOrgTest : AbstractIntegrationTest() {
    
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Transactional
    fun savePerson_savesAssociatedStaffOrg() {
        val expected = makePerson()

        personRepository.save(expected)

        val actual = personRepository.findAll()
        Assertions.assertThat(actual).hasSize(1)
        val actualStaffOrg = actual.first().staffOrg
        Assertions.assertThat(actualStaffOrg).isEqualTo(expected.staffOrg)
    }

    @Test
    @Transactional
    fun deleteStaffOrg_doesNotDeleteAssociatedPerson() {
        val expected = makePerson()

        personRepository.save(expected)
        entityManager
            .createNativeQuery("DELETE FROM staff_org WHERE id = ${expected.staffOrg?.id}")
            .executeUpdate()
        entityManager.flush()

        val actual = personRepository.findAll()
        Assertions.assertThat(actual).hasSize(1)
        val actualPerson = actual.first()
        Assertions.assertThat(actualPerson).isEqualTo(expected)
    }
}
