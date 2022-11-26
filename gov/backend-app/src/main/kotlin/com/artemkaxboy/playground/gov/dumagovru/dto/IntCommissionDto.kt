package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.IntCommission
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntCommissionDto(

    val id: Long,

    @SerialName("url_website")
    val urlWebsite: String,

    val lead: String,

    val title: String,

    @SerialName("countries_ids")
    val countries: Set<String>,

    val positions: Set<IntCommissionPositionDto>,
) {

    fun toEntity() = IntCommission(
        id = id,
        urlWebsite = urlWebsite.asPrintable(),
        lead = lead.asPrintable(),
        title = title.asPrintable(),
    )
}
