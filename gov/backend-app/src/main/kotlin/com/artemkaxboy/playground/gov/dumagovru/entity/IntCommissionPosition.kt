package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class IntCommissionPosition(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",
) {

    override fun equals(other: Any?) = entityEquals { this to other }

    override fun hashCode() = entityHashCode { this }

    override fun toString() = entityToString { this }
}

fun makeIntCommissionPosition(
    id: Long = 1L,
    title: String = "title",
) = IntCommissionPosition(
    id = id,
    title = title,
)

fun main() {
    val e1 = makeIntCommissionPosition()
    val e2 = makeIntCommissionPosition()
    println("$e1 == $e2: ${e1 == e2}")

    val e3 = makeIntCommissionPosition()
    val e4 = makeIntCommissionPosition(id = 23L)
    println("$e3 == $e4: ${e3 == e4}")

    val e5 = makeIntCommissionPosition()
    val e6 = makeIntCommissionPosition(title = "title2")
    println("$e5 == $e6: ${e5 == e6}")
}
