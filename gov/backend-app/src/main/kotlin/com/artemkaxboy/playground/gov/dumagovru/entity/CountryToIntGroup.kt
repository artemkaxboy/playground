package com.artemkaxboy.playground.gov.dumagovru.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@IdClass(CountryToIntGroup.IdClass::class)
data class CountryToIntGroup(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val country: Country? = null,

    @Id
    @Column(name = "country_id", nullable = false, columnDefinition = "TEXT")
    val countryId: String? = country?.id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_group_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroup: IntGroup? = null,

    @Id
    @Column(name = "int_group_id", nullable = false)
    val intGroupId: Long? = intGroup?.id,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CountryToIntGroup

        return countryId == other.countryId
                && intGroupId == other.intGroupId
    }

    override fun hashCode(): Int = Objects.hash(countryId, intGroupId)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(countryId = $countryId , intGroupId = $intGroupId )"
    }

    data class IdClass(
        val countryId: String? = null,
        val intGroupId: Long? = null,
    ) : Serializable
}

fun makeCountryToIntGroup(
    country: Country? = makeCountry(),
    countryId: String? = country?.id,
    intGroup: IntGroup? = makeIntGroup(),
    intGroupId: Long? = intGroup?.id,
) = CountryToIntGroup(
    country = country,
    countryId = countryId,
    intGroup = intGroup,
    intGroupId = intGroupId,
)
