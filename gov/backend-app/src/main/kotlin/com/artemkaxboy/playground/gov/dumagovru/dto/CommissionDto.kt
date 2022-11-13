package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommissionDto(

    val id: Long,

    val title: String,

    val description: String,

    val type: String,

    @SerialName("url_website")
    val urlWebsite: String,

    @SerialName("short_title")
    val shortTitle: String?,
)
