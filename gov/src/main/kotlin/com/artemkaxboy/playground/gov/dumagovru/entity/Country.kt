package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.converter.StringSetConverter
import com.artemkaxboy.playground.gov.dumagovru.dto.CountryDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Country(

    @Id
    val id: String,
    @Column(columnDefinition = "TEXT")
    val title: String = "",
    @Column(columnDefinition = "TEXT")
    val url: String? = null,
    @Column(columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter::class)
    val associated: Set<String>? = null,
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

    companion object {
        fun fromDto(dto: CountryDto) = Country(
            id = dto.id,
            title = dto.title,
            url = dto.url,
            associated = dto.associated,
        )
    }
}