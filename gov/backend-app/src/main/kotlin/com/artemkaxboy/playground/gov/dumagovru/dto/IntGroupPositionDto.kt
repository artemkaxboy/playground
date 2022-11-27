package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.IntGroup
import com.artemkaxboy.playground.gov.dumagovru.entity.IntGroupPosition
import com.artemkaxboy.playground.gov.dumagovru.entity.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntGroupPositionDto(

    val id: Long,

    val title: String,

    @SerialName("persons_ids")
    val persons: Set<Long>,
) {

    fun toEntity(intGroupId: Long) = IntGroupPosition(
        id = id,
        intGroup = IntGroup(id = intGroupId),
        title = title.asPrintable(),
        people = persons.map { Person(it) }.toMutableSet(),
    )
}
