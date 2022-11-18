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
import javax.persistence.OneToOne

@Entity
@IdClass(PersonToIntCommissionPosition.IdClass::class)
data class PersonToIntCommissionPosition(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = person?.id,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_commission_position_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intCommissionPosition: IntCommissionPosition? = null,

    @Id
    @Column(name = "int_commission_position_id", nullable = false)
    val intCommissionPositionId: Long? = intCommissionPosition?.id,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PersonToIntCommissionPosition

        return personId == other.personId
                && intCommissionPositionId == other.intCommissionPositionId
    }

    override fun hashCode(): Int = Objects.hash(personId, intCommissionPositionId)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(personId = $personId , intCommissionPositionId = $intCommissionPositionId )"
    }

    data class IdClass(
        val personId: Long? = null,
        val intCommissionPositionId: Long? = null,
    ) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as IdClass

            return personId == other.personId
                    && intCommissionPositionId == other.intCommissionPositionId
        }

        override fun hashCode(): Int = Objects.hash(personId, intCommissionPositionId)

        @Override
        override fun toString(): String {
            return this::class.simpleName + "(personId = $personId , intCommissionPositionId = $intCommissionPositionId )"
        }
    }
}

fun makePersonToIntCommissionPosition(
    person: Person? = makePerson(),
    personId: Long? = person?.id,
    intCommissionPosition: IntCommissionPosition? = makeIntCommissionPosition(),
    intCommissionPositionId: Long? = intCommissionPosition?.id,
) =
    PersonToIntCommissionPosition(
        person = person,
        personId = personId,
        intCommissionPosition = intCommissionPosition,
        intCommissionPositionId = intCommissionPositionId,
    )
