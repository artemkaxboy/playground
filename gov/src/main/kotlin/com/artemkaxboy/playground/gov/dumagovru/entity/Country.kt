package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Country(

    @Id
    val id: String,

    @Column(columnDefinition = "TEXT")
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val url: String? = null,

    @OneToMany(mappedBy = "to", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val associatedTo: Set<CountryToCountry> = emptySet(),

    @OneToMany(mappedBy = "from", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val associatedFrom: Set<CountryToCountry> = emptySet(),
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
    associatedTo = associated?.map { CountryToCountry(fromId = id, toId = it) }?.toSet() ?: emptySet(),
)
