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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CommissionPosition

        return personId == other.personId
                && commissionId == other.commissionId
    }

    override fun hashCode(): Int = Objects.hash(personId, commissionId)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(personId = $personId , commissionId = $commissionId , positionText = $positionText )"
    }

    data class IdClass(

        @Id
        @Column(name = "person_id")
        val personId: Long? = null,

        @Id
        @Column(name = "commission_id")
        val commissionId: Long? = null,
    ) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as CommissionPosition

            return personId == other.personId
                    && commissionId == other.commissionId
        }

        override fun hashCode(): Int = Objects.hash(personId, commissionId)

        @Override
        override fun toString(): String {
            return this::class.simpleName + "(personId = $personId , commissionId = $commissionId )"
        }
    }
}

fun makeCommissionPosition(
    person: Person = makePerson(),
    personId: Long = person.id ?: 1L,
    commission: Commission = makeCommission(),
    commissionId: Long = commission.id ?: 1L,
    positionText: String = "positionText",
    positionType: String? = "positionType",
) = CommissionPosition(
    person = person,
    personId = personId,
    commission = commission,
    commissionId = commissionId,
    positionText = positionText,
    positionType = positionType,
)
