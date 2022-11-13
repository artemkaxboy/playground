package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Convocation(
    @Id
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "num_genitive", columnDefinition = "TEXT", nullable = false)
    val numGenitive: String = "",

    @Column(name = "deputies_url", columnDefinition = "TEXT")
    val deputiesUrl: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Convocation

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id, numGenitive = $numGenitive )"
    }
}
