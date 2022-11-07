package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.ConvocationDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Convocation(

    @Id
    val id: Long,
    @Column(name = "deputies_url", columnDefinition = "TEXT")
    val deputiesUrl: String,
    @Column(name = "num_genitive", columnDefinition = "TEXT")
    val numGenitive: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Convocation

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , numGenitive = $numGenitive )"
    }
}

fun ConvocationDto.toEntity() = Convocation(
    id = id,
    deputiesUrl = deputiesUrl,
    numGenitive = numGenitive,
)
