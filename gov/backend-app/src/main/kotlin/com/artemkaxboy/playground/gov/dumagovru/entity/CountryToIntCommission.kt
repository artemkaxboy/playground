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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CountryToIntCommission

        return countryId == other.countryId
                && intCommissionId == other.intCommissionId
    }

    override fun hashCode(): Int = Objects.hash(countryId, intCommissionId);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(countryId = $countryId , intCommissionId = $intCommissionId )"
    }

    data class IdClass(
        val countryId: String? = null,
        val intCommissionId: Long? = null,
    ) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
            other as CountryToIntCommission

            return countryId == other.countryId
                    && intCommissionId == other.intCommissionId
        }

        override fun hashCode(): Int = Objects.hash(countryId, intCommissionId);

        @Override
        override fun toString(): String {
            return this::class.simpleName + "(countryId = $countryId , intCommissionId = $intCommissionId )"
        }
    }
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
