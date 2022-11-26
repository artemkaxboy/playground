package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.StaffOrg
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaffOrgDto(
    @SerialName("org_title")
    val orgTitle: String?,

    val org: Long,
) {

    fun toEntity() = StaffOrg(
        id = org,
        title = orgTitle?.asPrintable(),
    )
}
