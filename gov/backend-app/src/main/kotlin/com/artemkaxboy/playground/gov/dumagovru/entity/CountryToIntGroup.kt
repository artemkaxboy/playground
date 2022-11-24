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
@IdClass(CountryToIntGroup.IdClass::class)
data class CountryToIntGroup(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val country: Country? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "int_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroup: IntGroup? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val country: String? = null,
        val intGroup: Long? = null,
    ) : Serializable
}

fun makeCountryToIntGroup(
    country: Country? = makeCountry(),
    intGroup: IntGroup? = makeIntGroup(),
) = CountryToIntGroup(
    country = country,
    intGroup = intGroup,
)
