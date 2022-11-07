package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.converter.StringSetConverter
import com.artemkaxboy.playground.gov.dumagovru.dto.IntGroupDto
import org.hibernate.Hibernate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class IntGroup(

    @Id
    val id: Long,

    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String,

    @Column(columnDefinition = "TEXT")
    val lead: String,
    @Column(columnDefinition = "TEXT")
    val title: String,

    @Column(name = "countries_ids", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter::class) // TODO add link one-to-many
    val countries: Set<String>,

    @OneToMany(mappedBy = "intGroup", cascade = [CascadeType.ALL])
    val positions: Set<IntGroupPosition>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as IntGroup

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}

fun IntGroupDto.toEntity(): IntGroup {
    val group = IntGroup(
        id = id,
        urlWebsite = urlWebsite,
        lead = lead,
        title = title,
        countries = countries,
        emptySet(),
    )
    return group.copy(positions = positions.map { it.toEntity(group) }.toSet())
}
