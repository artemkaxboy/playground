package com.artemkaxboy.playground.gov.dumagovru.dto

import com.artemkaxboy.playground.gov.dumagovru.entity.Country
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(

    val id: String,

    val title: String,

    val url: String?,

    val associated: Set<String>?,
) {

    fun toEntity() = Country(
        id = id,
        title = title.asPrintable(),
        url = url?.asUrl(DUMA_GOV_RU)?.asPrintable(),
    )
}

fun Collection<CountryDto>.toEntities(): List<Country> {

    val entitiesWithEmptyAssociation = map { it.toEntity() }.associateBy { it.id!! }

    return mapNotNull { dto ->
        entitiesWithEmptyAssociation[dto.id]?.apply {
            dto.associated
                ?.mapNotNull { entitiesWithEmptyAssociation[it] }
                ?.let { associatedCountries.addAll(it) }
        }
    }
}
