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
@IdClass(CommissionPosition.IdClass::class)
data class CommissionPosition(

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commission_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val commission: Organisation? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "commission_position_to_region",
        foreignKey = ForeignKey(
            name = "fk_commission_position_to_region", // creates without cascade if no name
            foreignKeyDefinition = "FOREIGN KEY (person_id, commission_id) " +
                    "REFERENCES commission_position ON DELETE CASCADE"
        ),
        joinColumns = [JoinColumn(name = "commission_id"), JoinColumn(name = "person_id")],
    )
    val regions: MutableSet<Region> = mutableSetOf(),

    @Column(columnDefinition = "TEXT", name = "position_text", nullable = false)
    val positionText: String = "",

    @Column(columnDefinition = "TEXT", name = "position_type")
    val positionType: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val person: Long? = null,
        val commission: Long? = null,
    ) : Serializable
}

fun makeCommissionPosition(
    person: Person = makePerson(),
    commission: Organisation = makeOrganisation(),
    positionText: String = CommissionPosition::positionText.name,
    positionType: String? = CommissionPosition::positionType.name,
) = CommissionPosition(
    person = person,
    commission = commission,
    positionText = positionText,
    positionType = positionType,
)
