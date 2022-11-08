package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.CommissionPositionDto
import org.hibernate.Hibernate
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@IdClass(CommissionPosition.CommissionPositionId::class)
@Entity
data class CommissionPosition(

    @Id
    val org: Long,

    @Id
    @Column(name = "person_id")
    val personId: Long,

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    val person: Person? = null,

    @Column(name = "regions_title", columnDefinition = "TEXT")
    val regionsTitle: String = "",

    @Column(name = "org_title", columnDefinition = "TEXT")
    val orgTitle: String = "",

    @Column(name = "position_type", columnDefinition = "TEXT")
    val positionType: String? = null,

    @Column(name = "position_text", columnDefinition = "TEXT")
    val positionText: String = "",

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "commission_position_to_region",
        joinColumns = [JoinColumn(name = "org"),
            JoinColumn(name = "person_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    val regions: Set<Region> = emptySet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CommissionPosition

        return org == other.org
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(org = $org , orgTitle = $orgTitle , positionText = $positionText , personId = $personId )"
    }

    data class CommissionPositionId(

        @Id
        val org: Long = 0,

        @Id
        @Column(name = "person_id")
        val personId: Long = 0,
    ) : Serializable
}

fun CommissionPositionDto.toEntity(person: Person) = CommissionPosition(
    regionsTitle = regionsTitle,
    orgTitle = orgTitle,
    positionType = positionType,
    positionText = positionText,
    org = org,
    personId = person.id,
    person = person,
    regions = regions.map { Region(it) }.toSet(),
)
