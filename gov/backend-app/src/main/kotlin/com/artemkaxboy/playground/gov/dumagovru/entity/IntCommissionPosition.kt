package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class IntCommissionPosition(

    @Id
    val id: Long? = null,

    @Column(columnDefinition = "TEXT")
    val title: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IntCommissionPosition

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun makeIntCommissionPosition(
    id: Long = 1L,
    title: String = "title",
) = IntCommissionPosition(
    id = id,
    title = title,
)
