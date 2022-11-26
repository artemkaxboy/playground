package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Region
import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(

    val id: String,

    val title: String,
) {

    fun toEntity() = Region(
        id = id.asPrintable(),
        title = title.asPrintable(),
    )
}
