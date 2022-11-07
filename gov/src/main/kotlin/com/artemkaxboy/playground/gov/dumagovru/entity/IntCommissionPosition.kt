package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.converter.LongSetConverter
import com.artemkaxboy.playground.gov.dumagovru.dto.IntCommissionPositionDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class IntCommissionPosition(

    @Id
    val id: Long,

    @Column(columnDefinition = "TEXT")
    val title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_commission_id")
    val intCommission: IntCommission,

//    @Column(name = "int_commission_id")
//    val intCommissionId: Long = intCommission?.id ?: 0,

    @Column(name = "person_id", columnDefinition = "TEXT")
    @Convert(converter = LongSetConverter::class) // TODO add link one-to-many
    val persons: Set<Long>?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IntCommissionPosition

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun IntCommissionPositionDto.toEntity(intCommission: IntCommission) = IntCommissionPosition(
    id = id,
    title = title,
    persons = persons,
    intCommission = intCommission,
)
