package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(

    val id: String,

    val title: String,

    val url: String?,

    val associated: Set<String>?,
)
