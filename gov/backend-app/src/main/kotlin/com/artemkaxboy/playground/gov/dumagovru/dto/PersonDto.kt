package com.artemkaxboy.playground.gov.dumagovru.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(

    val id: Long,
    @SerialName("commission_positions")
    val commissionPositions: Set<CommissionPositionDto>,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("second_name")
    val secondName: String,
    @SerialName("last_name")
    val lastName: String,

    val photo: String?,
    @SerialName("original_ais_person_id")
    val originalAisPersonId: Long?,

    val lead: String,

    val title: String,

    val url: String,

    @SerialName("staff_org")
    val staffOrg: StaffOrgDto?,
    @SerialName("fraction_positions")
    val fractionPositions: Set<FractionPositionDto> = emptySet(),
    @SerialName("staff_positions")
    val staffPositions: Set<StaffPositionDto> = emptySet(),
)
