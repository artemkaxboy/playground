package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Convocation(

    @Id
    @Column(name = "id", nullable = false)
    val id: Int? = null,

    @Column(name = "num_genitive", columnDefinition = "TEXT", nullable = false)
    val numGenitive: String = "",

    @Column(name = "deputies_url", columnDefinition = "TEXT")
    val deputiesUrl: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeConvocation(
    id: Int = 1,
    numGenitive: String = "numGenitive",
    deputiesUrl: String? = "https://deputiesUrl",
) = Convocation(
    id = id,
    numGenitive = numGenitive,
    deputiesUrl = deputiesUrl,
)
