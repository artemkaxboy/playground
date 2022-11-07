package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConvocationDto(

    val id: Long,

    @SerialName("deputies_url")
    val deputiesUrl: String,

    @SerialName("num_genitive")
    val numGenitive: String,
)
