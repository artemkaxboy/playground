package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class StaffOrg(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT")
    val title: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeStaffOrg(
    id: Long = 1L,
    title: String = "title",
) = StaffOrg(
    id = id,
    title = title,
)
