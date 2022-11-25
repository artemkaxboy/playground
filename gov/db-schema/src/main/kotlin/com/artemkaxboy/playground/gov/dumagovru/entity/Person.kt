package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Person(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT", name = "first_name", nullable = false)
    val firstName: String = "",

    @Column(columnDefinition = "TEXT", name = "second_name")
    val secondName: String? = null,

    @Column(columnDefinition = "TEXT", name = "last_name", nullable = false)
    val lastName: String = "",

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val lead: String? = null,

    @Column(name = "original_ais_person_id")
    val originalAisPersonId: Long? = null,

    @Column(columnDefinition = "TEXT")
    val photo: String? = null,

    @Column(columnDefinition = "TEXT")
    val url: String? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makePerson(
    id: Long = 1L,
    firstName: String = "firstName",
    secondName: String? = "secondName",
    lastName: String = "lastName",
    title: String = "title",
    lead: String? = "lead",
    originalAisPersonId: Long? = 1L,
    photo: String? = "https://photo$id",
    url: String? = "https://url$id",
): Person = Person(
    id = id,
    firstName = firstName,
    secondName = secondName,
    lastName = lastName,
    title = title,
    lead = lead,
    originalAisPersonId = originalAisPersonId,
    photo = photo,
    url = url,
)
