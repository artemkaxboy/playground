package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.StaffOrgDto
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class StaffOrg(
    @Id
    val org: Long,

    @Column(name = "org_title", columnDefinition = "TEXT")
    val orgTitle: String?,

    @OneToMany(mappedBy = "staffOrg")
    val persons: Set<Person> = emptySet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as StaffOrg

        return org == other.org
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(org = $org , orgTitle = $orgTitle )"
    }
}

fun StaffOrgDto.toEntity() = StaffOrg(
    org = org,
    orgTitle = orgTitle,
)
