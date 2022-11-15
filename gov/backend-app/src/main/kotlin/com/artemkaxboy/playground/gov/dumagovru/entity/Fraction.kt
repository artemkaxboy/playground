package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Fraction(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(name = "short_title", columnDefinition = "TEXT")
    val shortTitle: String? = null,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String? = null,

    @Column(columnDefinition = "TEXT")
    val type: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Fraction

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun makeFraction(
    id: Long = 1L,
    title: String = "title",
    shortTitle: String? = "shortTitle",
    description: String? = "description",
    urlWebsite: String? = "https://urlWebsite",
    type: String? = "type",
) = Fraction(
    id = id,
    title = title,
    shortTitle = shortTitle,
    description = description,
    urlWebsite = urlWebsite,
    type = type,
)
