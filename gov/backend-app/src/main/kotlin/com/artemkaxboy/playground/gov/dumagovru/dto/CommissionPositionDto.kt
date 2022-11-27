package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Commission
import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommissionPositionDto(

    @SerialName("regions_title")
    val regionsTitle: String,
    @SerialName("org_title")
    val orgTitle: String,
    @SerialName("position_type")
    val positionType: String?,
    @SerialName("position_text")
    val positionText: String,

    val regions: List<String>,

    val org: Long,
) {

    fun toEntity(
        personId: Long,
    ) = CommissionPosition(
        person = Person(id = personId),
        commission = Commission(id = org),
        positionText = positionText.asPrintable(),
        positionType = positionType?.asPrintable(),
    )
}
