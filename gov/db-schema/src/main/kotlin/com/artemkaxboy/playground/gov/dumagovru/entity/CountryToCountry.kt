package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
@IdClass(CountryToCountry.IdClass::class)
data class CountryToCountry(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "from_country_id", nullable = false, columnDefinition = "TEXT")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fromCountry: Country? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "to_country_id", nullable = false, columnDefinition = "TEXT")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val toCountry: Country? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val fromCountry: String? = null,
        val toCountry: String? = null,
    ) : Serializable
}

fun makeCountryToCounty(
    fromCountry: Country = makeCountry("C1"),
    toCountry: Country = makeCountry("C2"),
) = CountryToCountry(
    fromCountry = fromCountry,
    toCountry = toCountry,
)
