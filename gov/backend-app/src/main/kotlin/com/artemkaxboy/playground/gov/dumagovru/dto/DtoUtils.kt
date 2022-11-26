package com.artemkaxboy.playground.gov.dumagovru.dto

const val DUMA_GOV_RU = "http://duma.gov.ru/"

fun String.asUrl(baseUrl: String): String? {

    if (this.isBlank()) return null
    if (this.startsWith("http")) return this

    return baseUrl.trimEnd('/') + '/' + this.trimStart('/')
}

fun String.asPrintable(): String {

    return this.replace(' ', ' ').trim()
}
