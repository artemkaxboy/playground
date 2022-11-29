package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class Country(

    @Id
    @Column(columnDefinition = "TEXT", nullable = false)
    val id: String? = null,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val url: String? = null,

    @ManyToMany(
        cascade = [/*CascadeType.DETACH*/ /*CascadeType.PERSIST*/ CascadeType.MERGE /*CascadeType.REFRESH*/],
        fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
        name = "country_to_country",
        inverseJoinColumns = [JoinColumn(
            name = "associated_country_id",
            foreignKey = ForeignKey(foreignKeyDefinition = "foreign key (associated_country_id) references country on delete cascade")
        )],
    )
    val associatedCountries: MutableSet<Country> = mutableSetOf(),
) {


    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeCountry(
    id: String = "XX",
    title: String = "country",
    url: String? = "https://country",
) = Country(
    id = id,
    title = title,
    url = url,
)
