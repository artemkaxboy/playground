package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CountryToCountry

        return fromCountyId == other.fromCountyId && toCountyId == other.toCountyId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(fromCountyId = $fromCountyId , toCountyId = $toCountyId )"
    }

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
