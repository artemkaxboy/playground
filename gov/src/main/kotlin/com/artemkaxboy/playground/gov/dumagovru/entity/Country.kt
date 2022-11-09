package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
data class Country(

    @Id
    val id: String,

    @Column(columnDefinition = "TEXT")
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val url: String? = null,

    @ManyToMany
    @JoinTable(
        name = "country_to_country",
        joinColumns = [JoinColumn(name = "from_id")],
        inverseJoinColumns = [JoinColumn(name = "to_id")]
    )
    val associated: Set<Country> = emptySet()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Country

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun CountryDto.toEntity(): Country = Country(
    id = id,
    title = title,
    url = url,
    associated = associated?.map { Country(it) }?.toSet() ?: emptySet(),
)
