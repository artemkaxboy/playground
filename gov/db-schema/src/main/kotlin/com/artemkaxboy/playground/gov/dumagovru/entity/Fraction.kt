package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
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

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
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
