package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.FractionPositionDto
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

@IdClass(FractionPositionId::class)
@Entity
data class FractionPosition(

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "fraction_position_to_region",
        joinColumns = [JoinColumn(name = "org"),
            JoinColumn(name = "convocation"),
            JoinColumn(name = "person_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    val regions: Set<Region> = emptySet(),

    @Column(name = "place_in_hall_column", columnDefinition = "TEXT")
    val placeInHallColumn: Int?,

    @Column(name = "place_in_hall_row", columnDefinition = "TEXT")
    val placeInHallRow: Int?,

    val actual: Boolean,

    @Column(name = "regions_title", columnDefinition = "TEXT")
    val regionsTitle: String,

    @Column(name = "org_title", columnDefinition = "TEXT")
    val orgTitle: String?,

    @Id
    val org: Long,

    @Id
    val convocation: Int,

    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val person: Person?,

    @Id
    @Column(name = "person_id")
    val personId: Long = person?.id ?: 0,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FractionPosition

        return org == other.org
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(org = $org , orgTitle = $orgTitle , convocation = $convocation )"
    }
}

data class FractionPositionId(
    @Id
    val org: Long = 0,
    @Id
    val convocation: Int = 0,
    @Id
    @Column(name = "person_id")
    val personId: Long = 0,
) : Serializable


fun FractionPositionDto.toEntity(person: Person): FractionPosition {
    val fractionPosition = FractionPosition(
        placeInHallColumn = placeInHallColumn,
        placeInHallRow = placeInHallRow,
        actual = actual,
        regionsTitle = regionsTitle,
        orgTitle = orgTitle,
        org = org,
        convocation = convocation,
        person = person,
    )

    return fractionPosition.copy(regions = regions.map { Region(it) }.toSet())
}
