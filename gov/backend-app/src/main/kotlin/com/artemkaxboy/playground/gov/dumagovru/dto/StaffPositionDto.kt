package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaffPositionDto(
    @SerialName("org_title")
    val orgTitle: String?,

    val org: Long,

    val title: String,
)
