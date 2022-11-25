package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Country(

    @Id
    @Column(columnDefinition = "TEXT", nullable = false)
    val id: String? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val url: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeCountry(
    id: String = "XX",
    title: String = "country",
    url: String? = "https://country",
) = Country(
    id = id,
    title = title,
    url = url,
)
