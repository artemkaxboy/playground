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

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val personId: Long? = null,
        val intCommissionPositionId: Long? = null,
    ) : Serializable
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
