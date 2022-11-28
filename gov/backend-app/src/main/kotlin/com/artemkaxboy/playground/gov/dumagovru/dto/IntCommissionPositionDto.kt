package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.IntCommission
import com.artemkaxboy.playground.gov.dumagovru.entity.IntCommissionPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntCommissionPositionDto(

    val id: Long,
    val title: String,
    @SerialName("persons_ids")
    val persons: Set<Long>,
) {

    fun toEntity(intCommissionId: Long) = IntCommissionPosition(
        id = id,
        title = title.asPrintable(),
        intCommission = IntCommission(intCommissionId),
        people = persons.map { Person(it) }.toMutableSet(),
    )
}
