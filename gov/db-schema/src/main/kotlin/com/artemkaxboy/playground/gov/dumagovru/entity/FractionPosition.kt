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
import javax.persistence.OneToOne

@Entity
@IdClass(FractionPosition.IdClass::class)
data class FractionPosition(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "convocation_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val convocation: Convocation? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "fraction_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fraction: Fraction? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

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
        val convocation: Long? = null,
        val fraction: Long? = null,
        val person: Long? = null,
    ) : Serializable
}

fun makeFractionPosition(
    convocation: Convocation = makeConvocation(),
    fraction: Fraction = makeFraction(),
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
