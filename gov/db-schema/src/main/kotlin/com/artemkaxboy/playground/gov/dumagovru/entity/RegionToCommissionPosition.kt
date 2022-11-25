package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.OneToOne

@Entity
@IdClass(RegionToCommissionPosition.IdClass::class)
data class RegionToCommissionPosition(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "region_id", nullable = false, columnDefinition = "TEXT")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val region: Region? = null,

    @JoinColumns(
        JoinColumn(name = "person_id", insertable = false, updatable = false, nullable = false),
        JoinColumn(name = "commission_id", insertable = false, updatable = false, nullable = false),
    )
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val commissionPosition: CommissionPosition? = null,

    @Id
    @Column(name = "person_id")
    val personId: Long? = commissionPosition?.person?.id,

    @Id
    @Column(name = "commission_id")
    val commissionId: Long? = commissionPosition?.commission?.id,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val region: String? = null,
        val personId: Long? = null,
        val commissionId: Long? = null,
    ) : Serializable
}

fun makeRegionToCommissionPosition(
    region: Region = makeRegion(),
    commissionPosition: CommissionPosition = makeCommissionPosition(),
) = RegionToCommissionPosition(
    region = region,
    commissionPosition = commissionPosition,
)
