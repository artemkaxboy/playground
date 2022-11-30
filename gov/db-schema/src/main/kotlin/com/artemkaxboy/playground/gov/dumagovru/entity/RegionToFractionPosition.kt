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
@IdClass(RegionToFractionPosition.IdClass::class)
data class RegionToFractionPosition(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "region_id", nullable = false, columnDefinition = "TEXT")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val region: Region? = null,

    @JoinColumns(
        JoinColumn(name = "convocation_id", insertable = false, updatable = false, nullable = false),
        JoinColumn(name = "fraction_id", insertable = false, updatable = false, nullable = false),
        JoinColumn(name = "person_id", insertable = false, updatable = false, nullable = false),
    )
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fractionPosition: FractionPosition? = null,

    @Id
    @Column(name = "convocation_id", insertable = false, updatable = false)
    val convocationId: Int? = fractionPosition?.convocation?.id,

    @Id
    @Column(name = "fraction_id", insertable = false, updatable = false)
    val fractionId: Long? = fractionPosition?.fraction?.id,

    @Id
    @Column(name = "person_id", insertable = false, updatable = false)
    val personId: Long? = fractionPosition?.person?.id,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val region: String? = null,
        val convocationId: Int? = null,
        val fractionId: Long? = null,
        val personId: Long? = null,
    ) : Serializable
}

fun makeRegionToFractionPosition(
    region: Region = makeRegion(),
    fractionPosition: FractionPosition = makeFractionPosition(),
) = RegionToFractionPosition(
    region = region,
    fractionPosition = fractionPosition,
)
