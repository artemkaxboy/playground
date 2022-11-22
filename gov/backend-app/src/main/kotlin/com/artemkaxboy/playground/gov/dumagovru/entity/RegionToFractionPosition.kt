package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne

@Entity
@IdClass(RegionToFractionPosition.IdClass::class)
data class RegionToFractionPosition(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val region: Region? = null,

    @Id
    @Column(name = "region_id", nullable = false, columnDefinition = "TEXT")
    val regionId: String? = region?.id,

    @JoinColumns(
        JoinColumn(name = "convocation_id", insertable = false, updatable = false),
        JoinColumn(name = "fraction_id", insertable = false, updatable = false),
        JoinColumn(name = "person_id", insertable = false, updatable = false),
    )
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fractionPosition: FractionPosition? = null,

    @Id
    @Column(name = "convocation_id", nullable = false)
    val convocationId: Long? = fractionPosition?.convocationId,

    @Id
    @Column(name = "fraction_id", nullable = false)
    val fractionId: Long? = fractionPosition?.fractionId,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = fractionPosition?.personId,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val regionId: String? = null,
        val convocationId: Long? = null,
        val fractionId: Long? = null,
        val personId: Long? = null,
    ) : Serializable
}

fun makeRegionToFractionPosition(
    region: Region = makeRegion(),
    regionId: String = region.id!!,
    fractionPosition: FractionPosition = makeFractionPosition(),
    convocationId: Long = fractionPosition.convocationId!!,
    fractionId: Long = fractionPosition.fractionId!!,
    personId: Long = fractionPosition.personId!!,
) = RegionToFractionPosition(
    region = region,
    regionId = regionId,
    fractionPosition = fractionPosition,
    convocationId = convocationId,
    fractionId = fractionId,
    personId = personId,
)
