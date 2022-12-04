package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Organisation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaffOrgDto(
    
    @SerialName("org_title")
    val orgTitle: String?,

    val org: Long,
) {

    fun toEntity() = Organisation(
        id = org,
        title = orgTitle?.asPrintable() ?: "",
    )
}
