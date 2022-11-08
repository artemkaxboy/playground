package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.FractionDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Fraction(

    @Id
    val id: Long,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(columnDefinition = "TEXT")
    val title: String? = null,

    @Column(columnDefinition = "TEXT")
    val type: String? = null,

    @Column(name = "short_title", columnDefinition = "TEXT")
    val shortTitle: String? = null,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Fraction

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , shortTitle = $shortTitle )"
    }
}

fun FractionDto.toEntity() = Fraction(
    id = id,
    description = description,
    title = title,
    shortTitle = shortTitle,
    urlWebsite = urlWebsite,
    type = type,
)
