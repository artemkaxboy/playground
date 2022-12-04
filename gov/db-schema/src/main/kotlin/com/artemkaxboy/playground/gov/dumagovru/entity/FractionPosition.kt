package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
@IdClass(FractionPosition.IdClass::class)
data class FractionPosition(

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "convocation_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val convocation: Convocation? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fraction_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fraction: Organisation? = null,

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    val person: Person? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "fraction_position_to_region",
        foreignKey = ForeignKey(
            name = "fk_fraction_position_to_region", // creates without cascade if no name
            foreignKeyDefinition = "FOREIGN KEY (person_id, fraction_id, convocation_id) " +
                    "REFERENCES fraction_position(person_id, fraction_id, convocation_id) ON DELETE CASCADE"
        ),
        joinColumns = [
            // without referencedColumnName order of arguments may be mixed
            JoinColumn(name = "fraction_id", referencedColumnName = "fraction_id"),
            JoinColumn(name = "person_id", referencedColumnName = "person_id"),
            JoinColumn(name = "convocation_id", referencedColumnName = "convocation_id"),
        ]
    )
    val regions: MutableSet<Region> = mutableSetOf(),

    @Column(nullable = false)
    val actual: Boolean = false,

    @Column(name = "place_in_hall_column")
    val placeInHallColumn: Int? = null,

    @Column(name = "place_in_hall_row")
    val placeInHallRow: Int? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val convocation: Int? = null,
        val fraction: Long? = null,
        val person: Long? = null,
    ) : Serializable
}

fun makeFractionPosition(
    convocation: Convocation = makeConvocation(),
    fraction: Organisation = makeOrganisation(),
    person: Person = makePerson(),
    actual: Boolean = true,
    placeInHallColumn: Int? = 1,
    placeInHallRow: Int? = 1,
) = FractionPosition(
    convocation = convocation,
    fraction = fraction,
    person = person,
    actual = actual,
    placeInHallColumn = placeInHallColumn,
    placeInHallRow = placeInHallRow,
)
