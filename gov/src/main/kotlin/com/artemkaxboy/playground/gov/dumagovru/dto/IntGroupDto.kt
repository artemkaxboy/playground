package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntGroupDto(

    val id: Long,
    val lead: String,
    val title: String,
    val positions: Set<IntGroupPositionDto>,
    @SerialName("countries_ids")
    val countries: Set<String>,
    @SerialName("url_website")
    val urlWebsite: String,
)