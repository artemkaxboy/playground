package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
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
) {

    fun toEntity() = Commission(
        id = id,
        title = title.asPrintable(),
        description = description.asPrintable(),
        type = type.asPrintable(),
        urlWebsite = urlWebsite.asUrl(DUMA_GOV_RU)?.asPrintable(),
        shortTitle = shortTitle?.asPrintable(),
    )
}
