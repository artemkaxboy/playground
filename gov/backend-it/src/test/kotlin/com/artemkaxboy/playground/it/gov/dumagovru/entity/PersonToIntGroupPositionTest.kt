package com.artemkaxboy.playground.it.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.repository.IntGroupPositionRepository
import com.artemkaxboy.playground.gov.dumagovru.repository.PersonRepository
import com.artemkaxboy.playground.it.AbstractIntegrationTest
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

internal class PersonToIntGroupPositionTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var intGroupPositionRepository: IntGroupPositionRepository

//    @Autowired
//    private lateinit var personToIntGroupPositionRepository: PersonToIntGroupPositionRepository

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var entityManager: EntityManager

//    @Test
//    @Transactional
//    fun deleteIntGroupPosition_deletesAssociatedPersonToIntGroupPosition() {
//        val expected = makePersonToIntGroupPosition()
//
//        personToIntGroupPositionRepository.save(expected)
//        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//        personToIntGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//
//        entityManager
//            .createNativeQuery("DELETE FROM int_group_position WHERE id = ${expected.intGroupPosition?.id}")
//            .executeUpdate()
//        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
//        personToIntGroupPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
//        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//    }

//    @Test
//    @Transactional
//    fun deletePerson_deletesAssociatedPersonToIntGroupPosition() {
//        val expected = makePersonToIntGroupPosition()
//
//        personToIntGroupPositionRepository.save(expected)
//        personRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//        personToIntGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//
//        entityManager.createNativeQuery("DELETE FROM person WHERE id = ${expected.person?.id}").executeUpdate()
//        personRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
//        personToIntGroupPositionRepository.findAll().let { Assertions.assertThat(it).isEmpty() }
//        intGroupPositionRepository.findAll().let { Assertions.assertThat(it).hasSize(1) }
//    }
}
