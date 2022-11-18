package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class IntCommission(

    @Id
    @Column(nullable = false)
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
        other as IntCommission

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun makeIntCommission(
    id: Long = 1L,
    title: String = "title",
    lead: String? = "lead",
    urlWebsite: String? = "urlWebsite",
) = IntCommission(
    id = id,
    title = title,
    lead = lead,
    urlWebsite = urlWebsite,
)
