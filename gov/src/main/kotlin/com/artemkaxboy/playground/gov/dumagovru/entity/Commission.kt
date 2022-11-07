package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.CommissionDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Commission(

    @Id
    val id: Long,
    @Column(columnDefinition = "TEXT")
    val title: String,
    @Column(columnDefinition = "TEXT")
    val description: String,
    @Column(columnDefinition = "TEXT")
    val type: String,
    @Column(name = "url_website", columnDefinition = "TEXT")
    val urlWebsite: String,
    @Column(name = "short_title", nullable = true, columnDefinition = "TEXT")
    val shortTitle: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Commission

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }

    companion object {
        fun fromDto(dto: CommissionDto) = Commission(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            type = dto.type,
            urlWebsite = dto.urlWebsite,
            shortTitle = dto.shortTitle,
        )
    }
}
