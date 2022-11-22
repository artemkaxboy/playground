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
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
@IdClass(CommissionPosition.IdClass::class)
data class CommissionPosition(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @Column(name = "person_id", nullable = false)
    val personId: Long? = person?.id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commission_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val commission: Commission? = null,

    @Id
    @Column(name = "commission_id", nullable = false)
    val commissionId: Long? = commission?.id,

    @Column(columnDefinition = "TEXT", name = "position_text", nullable = false)
    val positionText: String = "",

    @Column(columnDefinition = "TEXT", name = "position_type")
    val positionType: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val personId: Long? = null,
        val commissionId: Long? = null,
    ) : Serializable
}

fun makeCommissionPosition(
    person: Person = makePerson(),
    personId: Long = person.id ?: 1L,
    commission: Commission = makeCommission(),
    commissionId: Long = commission.id ?: 1L,
    positionText: String = CommissionPosition::positionText.name,
    positionType: String? = CommissionPosition::positionType.name,
) = CommissionPosition(
    person = person,
    personId = personId,
    commission = commission,
    commissionId = commissionId,
    positionText = positionText,
    positionType = positionType,
)
