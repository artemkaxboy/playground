package com.artemkaxboy.playground.it.gov.dumagovru.it

import com.artemkaxboy.playground.gov.GovApplication
import org.springframework.boot.test.context.SpringBootTest

/**
 * Base composite annotation for integration tests.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [GovApplication::class])
annotation class IntegrationTest 
