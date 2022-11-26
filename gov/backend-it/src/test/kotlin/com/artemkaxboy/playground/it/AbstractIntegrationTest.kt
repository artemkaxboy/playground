package com.artemkaxboy.playground.it

import com.artemkaxboy.playground.gov.GovApplication
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer

//https://github.com/testcontainers/testcontainers-java/blob/main/examples/spring-boot-kotlin-redis/src/test/kotlin/com/example/redis/RedisTest.kt

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [GovApplication::class])
@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])
abstract class AbstractIntegrationTest {

    companion object {
        val container = PostgreSQLContainer<Nothing>("postgres:12.0").apply {
            withDatabaseName("database")
            withUsername("username")
            withPassword("password")
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            container.start()

            TestPropertyValues.of(
                "spring.datasource.url=${container.jdbcUrl}",
                "spring.datasource.password=${container.password}",
                "spring.datasource.username=${container.username}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
