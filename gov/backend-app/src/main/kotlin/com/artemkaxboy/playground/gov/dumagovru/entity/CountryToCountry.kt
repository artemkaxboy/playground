package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.Column
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
    @Column(name = "from_country_id", nullable = false)
    val fromCountyId: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_country_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fromCountry: Country? = null,

    @Id
    @Column(name = "to_country_id", nullable = false)
    val toCountyId: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_country_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val toCountry: Country? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val fromCountyId: String? = null,
        val toCountyId: String? = null,
    ) : Serializable
}

fun makeCountryToCounty(
    fromCountry: Country = makeCountry("C1"),
    fromCountyId: String = fromCountry.id!!,
    toCountry: Country = makeCountry("C2"),
    toCountyId: String = toCountry.id!!,
) = CountryToCountry(
    fromCountyId = fromCountyId,
    fromCountry = fromCountry,
    toCountyId = toCountyId,
    toCountry = toCountry,
)
