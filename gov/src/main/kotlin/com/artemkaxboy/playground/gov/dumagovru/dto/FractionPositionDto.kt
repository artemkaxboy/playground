package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FractionPositionDto(

    val regions: Set<String> = emptySet(),

    @SerialName("place_in_hall_column")
    val placeInHallColumn: Int?,

    @SerialName("place_in_hall_row")
    val placeInHallRow: Int?,

    val actual: Boolean,

    @SerialName("regions_title")
    val regionsTitle: String,

    @SerialName("org_title")
    val orgTitle: String?,

    val org: Long,

    val convocation: Int,
)
