package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConvocationDto(

    val id: Long,

    @SerialName("deputies_url")
    val deputiesUrl: String,

    @SerialName("num_genitive")
    val numGenitive: String,
) {

    fun toEntity() = Convocation(
        id = id,
        deputiesUrl = deputiesUrl.asUrl(DUMA_GOV_RU)?.asPrintable(),
        numGenitive = numGenitive.asPrintable(),
    )
}
