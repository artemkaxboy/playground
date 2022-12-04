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
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

const val NO_ID = -1L

@Entity
data class Person(

    @Id
    @Column(nullable = false)
    val id: Long = NO_ID,

    @Column(columnDefinition = "TEXT", name = "first_name", nullable = false)
    val firstName: String = "",

    @Column(columnDefinition = "TEXT", name = "second_name")
    val secondName: String? = null,

    @Column(columnDefinition = "TEXT", name = "last_name", nullable = false)
    val lastName: String = "",

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val lead: String? = null,

    @Column(name = "original_ais_person_id")
    val originalAisPersonId: Long? = null,

    @Column(columnDefinition = "TEXT")
    val photo: String? = null,

    @Column(columnDefinition = "TEXT")
    val url: String? = null,

    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(
        name = "staff_org_id",
        foreignKey = ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (staff_org_id) REFERENCES organisation ON DELETE SET NULL"
        )
    )
    val staffOrg: Organisation? = null,

    @OneToMany(mappedBy = "person", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    val commissionPositions: MutableSet<CommissionPosition> = mutableSetOf(),

    @ManyToMany(mappedBy = "people", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroupPositions: MutableSet<IntGroupPosition> = mutableSetOf(),

    @ManyToMany(mappedBy = "people", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intCommissionPositions: MutableSet<IntCommissionPosition> = mutableSetOf(),

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @OnDelete(action = OnDeleteAction.CASCADE)
    val fractionPositions: MutableSet<FractionPosition> = mutableSetOf(),
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makePerson(
    id: Long = 1L,
    firstName: String = "firstName",
    secondName: String? = "secondName",
    lastName: String = "lastName",
    title: String = "title",
    lead: String? = "lead",
    originalAisPersonId: Long? = 1L,
    photo: String? = "https://photo$id",
    url: String? = "https://url$id",
    staffOrg: Organisation? = makeOrganisation(),
): Person = Person(
    id = id,
    firstName = firstName,
    secondName = secondName,
    lastName = lastName,
    title = title,
    lead = lead,
    originalAisPersonId = originalAisPersonId,
    photo = photo,
    url = url,
    staffOrg = staffOrg,
)
