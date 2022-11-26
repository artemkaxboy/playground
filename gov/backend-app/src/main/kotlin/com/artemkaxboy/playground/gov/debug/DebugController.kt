package com.artemkaxboy.playground.gov.debug

import com.artemkaxboy.playground.gov.dumagovru.DbInitializer
import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugController(
    private val dbInitializer: DbInitializer,
) {

    @GetMapping("/debug")
    fun debug(): String {

        return dbInitializer.init().let { "OK" }
    }
}
