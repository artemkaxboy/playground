package com.artemkaxboy.playground.gov.debug

import com.artemkaxboy.playground.gov.dumagovru.repository.CountryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DebugController(
    private val countryRepository: CountryRepository,
) {

    @GetMapping("/debug")
    fun debug(): String {
        return countryRepository.findAll().joinToString { it.toString() }
    }
}
