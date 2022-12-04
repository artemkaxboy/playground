package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Organisation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ORG_TYPE = "fraction"

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
) {

    fun toEntity() = Organisation(
        id = id,
        description = description?.asPrintable(),
        title = title.asPrintable(),
        shortTitle = shortTitle?.asPrintable(),
        urlWebsite = urlWebsite.asUrl(DUMA_GOV_RU)?.asPrintable(),
        type = type.asPrintable(),
    )

    companion object {

        fun minimal(id: Long) = FractionDto(
            id = id,
            description = null,
            title = "",
            shortTitle = null,
            urlWebsite = "",
            type = ORG_TYPE,
        )
    }
}
