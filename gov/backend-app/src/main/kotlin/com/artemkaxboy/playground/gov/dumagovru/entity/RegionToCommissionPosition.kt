package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne

@Entity
@IdClass(RegionToCommissionPosition.IdClass::class)
data class RegionToCommissionPosition(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val region: Region? = null,

    @Id
    @Column(name = "region_id", nullable = false, columnDefinition = "TEXT")
    val regionId: String? = region?.id,

    @JoinColumns(
        JoinColumn(name = "person_id", insertable = false, updatable = false),
        JoinColumn(name = "commission_id", insertable = false, updatable = false),
    )
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val commissionPosition: CommissionPosition? = null,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = commissionPosition?.personId,

    @Id
    @Column(name = "commission_id", nullable = false)
    val commissionId: Long? = commissionPosition?.commissionId,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RegionToCommissionPosition

        return regionId == other.regionId
                && personId == other.personId
                && commissionId == other.commissionId
    }

    override fun hashCode(): Int = Objects.hash(regionId, personId, commissionId)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(regionId = $regionId , personId = $personId , commissionId = $commissionId , region = $region )"
    }

    data class IdClass(

        @Id
        @Column(name = "region_id", nullable = false, columnDefinition = "TEXT")
        val regionId: String? = null,

        @Id
        @Column(name = "person_id", nullable = false)
        val personId: Long? = null,

        @Id
        @Column(name = "commission_id", nullable = false)
        val commissionId: Long? = null,
    ) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as RegionToCommissionPosition

            return regionId == other.regionId
                    && personId == other.personId
                    && commissionId == other.commissionId
        }

        override fun hashCode(): Int = Objects.hash(regionId, personId, commissionId)

        @Override
        override fun toString(): String {
            return this::class.simpleName + "(regionId = $regionId , personId = $personId , commissionId = $commissionId )"
        }
    }
}

fun makeRegionToCommissionPosition(
    region: Region = makeRegion(),
    regionId: String = region.id!!,
    commissionPosition: CommissionPosition = makeCommissionPosition(),
    personId: Long = commissionPosition.person!!.id!!,
    commissionId: Long = commissionPosition.commission!!.id!!,
) = RegionToCommissionPosition(
    region = region,
    regionId = regionId,
    commissionPosition = commissionPosition,
    personId = personId,
    commissionId = commissionId,
)
