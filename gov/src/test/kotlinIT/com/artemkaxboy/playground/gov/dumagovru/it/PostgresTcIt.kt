package com.artemkaxboy.playground.gov.dumagovru.it

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container


//https://www.baeldung.com/spring-boot-testcontainers-integration-test
//https://dev.to/j_a_o_v_c_t_r/testcontainers-with-mysql-and-redis-with-an-spring-boot-kotlin-application-4bmf
@SpringBootTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [PostgresTcIt.Initializer::class])

abstract class PostgresTcIt {

    companion object {

        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:11.1").apply {
            withDatabaseName("databaseName")
            withUsername("username")
            withPassword("password")
        }

//        @JvmStatic
//        @DynamicPropertySource
//        fun properties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url", postgresContainer::getUsername)
//            registry.add("spring.datasource.password", postgresContainer::getPassword)
//            registry.add("spring.datasource.username", postgresContainer::getUsername)
//        }
    }

    internal class Initializer :
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + postgresContainer.jdbcUrl,
                "spring.datasource.username=" + postgresContainer.username,
                "spring.datasource.password=" + postgresContainer.password
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
