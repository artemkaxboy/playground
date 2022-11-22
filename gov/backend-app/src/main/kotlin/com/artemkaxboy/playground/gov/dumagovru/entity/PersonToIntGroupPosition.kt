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
@IdClass(PersonToIntGroupPosition.IdClass::class)
data class PersonToIntGroupPosition(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = person?.id,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_group_position_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroupPosition: IntGroupPosition? = null,

    @Id
    @Column(name = "int_group_position_id", nullable = false)
    val intGroupPositionId: Long? = intGroupPosition?.id,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val personId: Long? = null,
        val intGroupPositionId: Long? = null,
    ) : Serializable
}

fun makePersonToIntGroupPosition(
    person: Person? = makePerson(),
    personId: Long? = person?.id,
    intGroupPosition: IntGroupPosition? = makeIntGroupPosition(),
    intGroupPositionId: Long? = intGroupPosition?.id,
) =
    PersonToIntGroupPosition(
        person = person,
        personId = personId,
        intGroupPosition = intGroupPosition,
        intGroupPositionId = intGroupPositionId,
    )
