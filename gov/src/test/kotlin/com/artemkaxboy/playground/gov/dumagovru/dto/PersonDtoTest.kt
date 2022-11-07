package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

internal class PersonDtoTest {

    @Test
    fun parseJson_ParsesSavedData() {
        val filename = "duma-gov-ru/data.json"
        val jsonString = javaClass.classLoader.getResource(filename)?.readText()
            ?: throw IllegalArgumentException("File $filename not found")

//        val json = Json { ignoreUnknownKeys = true }
        val json = Json { ignoreUnknownKeys = false }
        val data = json.decodeFromString<ApplicationDataDto>(jsonString)
        println(data)
    }
}
