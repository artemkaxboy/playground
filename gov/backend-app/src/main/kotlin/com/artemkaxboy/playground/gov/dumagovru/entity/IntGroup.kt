package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class IntGroup(

    @Id
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val lead: String? = null,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IntGroup

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun makeIntGroup(
    id: Long = 1L,
    title: String = "title",
    lead: String? = "lead",
    urlWebsite: String? = "https://urlWebsite",
) = IntGroup(
    id = id,
    title = title,
    lead = lead,
    urlWebsite = urlWebsite,
)
