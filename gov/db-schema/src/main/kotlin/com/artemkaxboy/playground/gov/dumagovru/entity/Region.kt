package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class Region(

    @Id
    @Column(columnDefinition = "TEXT", nullable = false)
    val id: String? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @ManyToMany(mappedBy = "regions", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val commissionPositions: MutableSet<CommissionPosition> = mutableSetOf(),

    @ManyToMany(mappedBy = "regions", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fractionPositions: MutableSet<FractionPosition> = mutableSetOf(),
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
