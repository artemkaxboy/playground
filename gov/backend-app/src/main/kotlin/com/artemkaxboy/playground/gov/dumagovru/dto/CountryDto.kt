package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import com.artemkaxboy.playground.gov.dumagovru.entity.CountryToCountry
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(

    val id: String,

    val title: String,

    val url: String?,

    val associated: Set<String>?,
) {

    fun toEntity() = Country(
        id = id,
        title = title.asPrintable(),
        url = url?.asUrl(DUMA_GOV_RU)?.asPrintable(),
    )

    fun toEntity(associated: Set<Country>) = CountryToCountry(
        fromCountry = associated.first { it.id == id },
        toCountry = associated.first { it.id == id },
    )
}
