package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Commission(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT", name = "short_title")
    val shortTitle: String? = null,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(columnDefinition = "TEXT")
    val type: String? = null,

    @Column(columnDefinition = "TEXT", name = "url_website")
    val urlWebsite: String? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Commission

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun makeCommission(
    id: Long = 1L,
    title: String = "title",
    shortTitle: String? = "shortTitle",
    description: String? = "description",
    type: String? = "type",
    urlWebsite: String? = "https://urlWebsite",
) = Commission(
    id = id,
    title = title,
    shortTitle = shortTitle,
    description = description,
    type = type,
    urlWebsite = urlWebsite,
)
