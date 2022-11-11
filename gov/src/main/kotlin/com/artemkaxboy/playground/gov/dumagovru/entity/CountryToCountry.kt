package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@IdClass(CountryToCountry.CountryToCountryId::class)
@Entity
data class CountryToCountry(

    @ManyToOne
    @JoinColumn(name = "from_id", insertable = false, updatable = false)
    val from: Country? = null,

    @Id
    @Column(name = "from_id", columnDefinition = "TEXT")
    val fromId: String = from?.id ?: "",

    @ManyToOne
    @JoinColumn(name = "to_id", insertable = false, updatable = false)
    val to: Country? = null,

    @Id
    @Column(name = "to_id", columnDefinition = "TEXT")
    val toId: String = to?.id ?: "",
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CountryToCountry

        return fromId == other.fromId && toId == other.toId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(fromId = $fromId , toId = $toId )"
    }

    data class CountryToCountryId(
        @Id
        @Column(name = "from_id")
        val fromId: String = "",
        @Id
        @Column(name = "to_id")
        val toId: String = "",
    ) : Serializable
}
