package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@IdClass(FractionPosition.IdClass::class)
data class FractionPosition(

    @ManyToOne
    @JoinColumn(name = "convocation_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val convocation: Convocation? = null,

    @Id
    @Column(name = "convocation_id", nullable = false)
    val convocationId: Long? = convocation?.id,

    @ManyToOne
    @JoinColumn(name = "fraction_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fraction: Fraction? = null,

    @Id
    @Column(name = "fraction_id", nullable = false)
    val fractionId: Long? = fraction?.id,

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = person?.id,

    @Column(nullable = false)
    val actual: Boolean = false,

    @Column(name = "place_in_hall_column")
    val placeInHallColumn: Int? = null,

    @Column(name = "place_in_hall_row")
    val placeInHallRow: Int? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FractionPosition

        return convocationId == other.convocationId
                && fractionId == other.fractionId
                && personId == other.personId
    }

    override fun hashCode(): Int = Objects.hash(convocationId, fractionId, personId);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(convocationId = $convocationId , fractionId = $fractionId , personId = $personId , actual = $actual )"
    }

    data class IdClass(
        val convocationId: Long? = null,
        val fractionId: Long? = null,
        val personId: Long? = null,
    ) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as FractionPosition

            return convocationId == other.convocationId
                    && fractionId == other.fractionId
                    && personId == other.personId
        }

        override fun hashCode(): Int = Objects.hash(convocationId, fractionId, personId);

        @Override
        override fun toString(): String {
            return this::class.simpleName + "(convocationId = $convocationId , fractionId = $fractionId , personId = $personId )"
        }
    }
}

fun makeFractionPosition(
    convocation: Convocation = makeConvocation(),
    convocationId: Long = convocation.id!!,
    fraction: Fraction = makeFraction(),
    fractionId: Long = fraction.id!!,
    person: Person = makePerson(),
    personId: Long = person.id!!,
    actual: Boolean = true,
    placeInHallColumn: Int = 1,
    placeInHallRow: Int = 1,
) = FractionPosition(
    convocation = convocation,
    convocationId = convocationId,
    fraction = fraction,
    fractionId = fractionId,
    person = person,
    personId = personId,
    actual = actual,
    placeInHallColumn = placeInHallColumn,
    placeInHallRow = placeInHallRow,
)
