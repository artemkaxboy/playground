package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Region(

    @Id
    @Column(columnDefinition = "TEXT", nullable = false)
    val id: String? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeRegion(
    id: String = "id",
    title: String = "title",
) = Region(
    id = id,
    title = title,
)
