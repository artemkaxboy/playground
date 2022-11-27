package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class IntGroup(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val lead: String? = null,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
        name = "int_group_to_country",
        inverseJoinColumns = [JoinColumn(
            name = "country_id",
            foreignKey = ForeignKey(foreignKeyDefinition = "foreign key (country_id) references country on delete cascade")
        )]
    )
    val countries: MutableSet<Country> = mutableSetOf(),
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeIntGroup(
    id: Long = 1L,
    title: String = "title",
    lead: String? = "lead",
    urlWebsite: String? = "https://urlWebsite",
    countries: Set<Country> = setOf(makeCountry()),
) = IntGroup(
    id = id,
    title = title,
    lead = lead,
    urlWebsite = urlWebsite,
    countries = countries.toMutableSet(),
)
