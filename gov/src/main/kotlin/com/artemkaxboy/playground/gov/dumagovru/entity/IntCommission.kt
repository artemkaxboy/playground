package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.converter.StringSetConverter
import com.artemkaxboy.playground.gov.dumagovru.dto.IntCommissionDto
import org.hibernate.Hibernate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class IntCommission(

    @Id
    val id: Long,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String = "",

    @Column(columnDefinition = "TEXT")
    val lead: String = "",

    @Column(columnDefinition = "TEXT")
    val title: String = "",

    @Column(name = "countries_ids", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter::class) // TODO add link one-to-many
    val countries: Set<String> = emptySet(),

    @OneToMany(mappedBy = "intCommission", cascade = [CascadeType.ALL])
    val positions: Set<IntCommissionPosition> = emptySet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IntCommission

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun IntCommissionDto.toEntity() = IntCommission(
    id = id,
    urlWebsite = urlWebsite,
    lead = lead,
    title = title,
    countries = countries,
    positions = positions.map { it.toEntity(IntCommission(id)) }.toSet(),
)
