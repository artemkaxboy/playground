package com.artemkaxboy.playground.gov.dumagovru.entity

internal class PersonTest

fun makePerson(
    id: Long = 1L,
    firstName: String = "firstName",
    secondName: String = "secondName",
    lastName: String = "lastName",
    title: String = "title",
    lead: String = "lead",
    originalAisPersonId: Long = 1L,
    photo: String = "https://photo$id",
    url: String = "https://url$id",
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
