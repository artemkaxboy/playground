package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Organisation(

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

    fun merge(other: Organisation?): Organisation {
        require(other == null || id == other.id) { "Can't merge organisations with different ids" }
        return copy(
            title = maxOf(title, other?.title, Comparator.comparingInt(::stringLengthOrNegative))!!,
            shortTitle = maxOf(shortTitle, other?.shortTitle, Comparator.comparingInt(::stringLengthOrNegative)),
            description = maxOf(description, other?.description, Comparator.comparingInt(::stringLengthOrNegative)),
            urlWebsite = maxOf(urlWebsite, other?.urlWebsite, Comparator.comparingInt(::stringLengthOrNegative)),
            type = maxOf(type, other?.type, Comparator.comparingInt(::stringLengthOrNegative)),
        )
    }

    fun stringLengthOrNegative(string: String?): Int {
        return string?.length ?: -1
    }

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeOrganisation(
    id: Long = 1L,
    title: String = "title",
    shortTitle: String? = "shortTitle",
    description: String? = "description",
    urlWebsite: String? = "https://urlWebsite",
    type: String? = "type",
) = Organisation(
    id = id,
    title = title,
    shortTitle = shortTitle,
    description = description,
    urlWebsite = urlWebsite,
    type = type,
)

/**
 * Combines two organisation sequences into one. Merges all known information about organisations.
 */
fun Sequence<Organisation>.merge(other: Sequence<Organisation>): Sequence<Organisation> {
    return plus(other).groupBy { it.id }
        .values
        .asSequence()
        .map { it.reduce { acc, organisation -> acc.merge(organisation) } }
}
