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

    private fun toEntity() = Country(
        id = id,
        title = title.asPrintable(),
        url = url?.asUrl(DUMA_GOV_RU)?.asPrintable(),
    )

    companion object {

        // it uses private fun toEntity()
        fun Sequence<CountryDto>.toEntities(): Sequence<Country> {

            val entitiesWithEmptyAssociation = map { it.toEntity() }.associateBy { it.id!! }

            return map { dto -> dto.id to (dto.associated ?: emptySet()) }
                .mapNotNull { (id, associatedSet) ->
                    val entity = entitiesWithEmptyAssociation[id]
                    entity?.associatedCountries?.addAll(associatedSet.mapNotNull { entitiesWithEmptyAssociation[it] })
                    entity
                }
        }
    }
}
