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

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

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
