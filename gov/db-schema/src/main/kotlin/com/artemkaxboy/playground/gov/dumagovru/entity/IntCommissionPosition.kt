package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class IntCommissionPosition(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "int_commission_id", nullable = false)
    val intCommission: IntCommission? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "int_commission_position_to_person",
        joinColumns = [JoinColumn(
            name = "int_commission_position_id",
            foreignKey = ForeignKey(foreignKeyDefinition = "foreign key (int_commission_position_id) references int_commission_position on delete cascade")
        )]
    )
    var people: MutableSet<Person> = mutableSetOf(),
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeIntCommissionPosition(
    id: Long = 1L,
    title: String = "title",
) = IntCommissionPosition(
    id = id,
    title = title,
)
