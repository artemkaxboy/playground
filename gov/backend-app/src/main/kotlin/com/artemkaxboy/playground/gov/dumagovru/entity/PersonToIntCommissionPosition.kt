package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
@IdClass(PersonToIntCommissionPosition.IdClass::class)
data class PersonToIntCommissionPosition(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "int_commission_position_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intCommissionPosition: IntCommissionPosition? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val person: Long? = null,
        val intCommissionPosition: Long? = null,
    ) : Serializable
}

fun makePersonToIntCommissionPosition(
    person: Person? = makePerson(),
    intCommissionPosition: IntCommissionPosition? = makeIntCommissionPosition(),
) = PersonToIntCommissionPosition(
    person = person,
    intCommissionPosition = intCommissionPosition,
)
