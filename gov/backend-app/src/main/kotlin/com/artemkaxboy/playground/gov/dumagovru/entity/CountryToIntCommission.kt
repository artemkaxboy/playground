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
@IdClass(CountryToIntCommission.IdClass::class)
data class CountryToIntCommission(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val country: Country? = null,

    @Id
    @Column(name = "country_id", nullable = false, columnDefinition = "TEXT")
    val countryId: String? = country?.id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_commission_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intCommission: IntCommission? = null,

    @Id
    @Column(name = "int_commission_id", nullable = false)
    val intCommissionId: Long? = intCommission?.id,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val countryId: String? = null,
        val intCommissionId: Long? = null,
    ) : Serializable
}

fun makeCountryToIntCommission(
    country: Country? = makeCountry(),
    countryId: String? = country?.id,
    intCommission: IntCommission? = makeIntCommission(),
    intCommissionId: Long? = intCommission?.id,
) = CountryToIntCommission(
    country = country,
    countryId = countryId,
    intCommission = intCommission,
    intCommissionId = intCommissionId,
)
