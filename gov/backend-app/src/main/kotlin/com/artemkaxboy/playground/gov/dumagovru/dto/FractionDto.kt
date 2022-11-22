package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FractionDto(

    val id: Long,

    val description: String?,

    val title: String,

    @SerialName("short_title")
    val shortTitle: String?,

    @SerialName("url_website")
    val urlWebsite: String,

    val type: String,
)