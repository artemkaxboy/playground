package com.artemkaxboy.playground.gov.dumagovru.entity

class CommissionTest {
}

fun makeCommission(
    id: Long = 1,
    title: String = "title",
    shortTitle: String = "shortTitle",
    description: String = "description",
    type: String = "type",
    urlWebsite: String = "https://urlWebsite$id",
): Commission = Commission(
    id = id,
    title = title,
    shortTitle = shortTitle,
    description = description,
    type = type,
    urlWebsite = urlWebsite,
)
