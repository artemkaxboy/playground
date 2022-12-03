package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Convocation
import com.artemkaxboy.playground.gov.dumagovru.entity.Fraction
import com.artemkaxboy.playground.gov.dumagovru.entity.FractionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import com.artemkaxboy.playground.gov.dumagovru.entity.Region
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
) {

    fun toEntity(personId: Long) = FractionPosition(
        convocation = Convocation(convocation),
        fraction = Fraction(org),
        person = Person(personId),
        actual = actual,
        placeInHallColumn = placeInHallColumn,
        placeInHallRow = placeInHallRow,
        regions = regions.map { Region(id = it) }.toMutableSet(),
    )
}
