package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class IntCommissionPosition(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeIntCommissionPosition(
    id: Long = 1L,
    title: String = "title",
) = IntCommissionPosition(
    id = id,
    title = title,
)