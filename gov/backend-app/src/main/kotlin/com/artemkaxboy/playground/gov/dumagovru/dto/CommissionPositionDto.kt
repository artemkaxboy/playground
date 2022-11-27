package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.CommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.makeCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.makePerson
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
        person = makePerson(personId),
        commission = makeCommission(org),
        positionText = positionText.asPrintable(),
        positionType = positionType?.asPrintable(),
    )
}
