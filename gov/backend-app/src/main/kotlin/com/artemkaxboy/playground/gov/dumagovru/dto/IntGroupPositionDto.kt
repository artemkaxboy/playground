package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntGroupPositionDto(

    val id: Long,
    val title: String,
    @SerialName("persons_ids")
    val persons: Set<Long>,
)
