package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.it.IntegrationTest
import com.artemkaxboy.playground.gov.dumagovru.it.PostgresTcIt
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@IntegrationTest
class CountryTest(
) : PostgresTcIt() {

    @Autowired
    lateinit var countryRepository: CountryRepository

    @Test
    fun `test`() {
        countryRepository.findAll()
    }
}

//@Testcontainers
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class CarControllerTest : DatabaseContainerConfiguration() {
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    companion object {
//        private const val URI = "/cars"
//    }
//
//    @Test
//    fun `should return 200 code when call cars`() {
//        mockMvc.get(URI).andExpect { status { isOk() } }
//    }
