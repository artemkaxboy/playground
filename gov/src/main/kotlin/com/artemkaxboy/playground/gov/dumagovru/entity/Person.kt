package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.PersonDto
import org.hibernate.Hibernate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Person(

    @Id
    val id: Long,

    @Column(name = "commission_positions")
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "person_to_commission_position",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "org"),
            JoinColumn(name = "person_id")]
    )
    val commissionPositions: Set<CommissionPosition> = emptySet(),

    @Column(name = "first_name", columnDefinition = "TEXT")
    val firstName: String = "",

    @Column(name = "second_name", columnDefinition = "TEXT")
    val secondName: String = "",

    @Column(name = "last_name", columnDefinition = "TEXT")
    val lastName: String = "",

    @Column(columnDefinition = "TEXT")
    val photo: String? = null,

    @Column(name = "original_ais_person_id")
    val originalAisPersonId: Long? = null,

    @Column(columnDefinition = "TEXT")
    val lead: String = "",

    @Column(columnDefinition = "TEXT")
    val title: String = "",

    @Column(columnDefinition = "TEXT")
    val url: String = "",

    @JoinColumn(name = "staff_org")
    @ManyToOne(cascade = [CascadeType.ALL])
    val staffOrg: StaffOrg? = null,

    @JoinColumn(name = "person_id")
    @OneToMany(cascade = [CascadeType.ALL])
    val fractionPositions: Set<FractionPosition> = emptySet(),

//    @Column(name = "staff_positions")
//    val staffPositions: Set<StaffPositionDto> = emptySet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Person

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , secondName = $secondName , lastName = $lastName )"
    }

}

fun PersonDto.toEntity(): Person {
    val idPerson = Person(id = id)
    return Person(
        id = id,
        firstName = firstName,
        secondName = secondName,
        lastName = lastName,
        photo = photo,
        originalAisPersonId = originalAisPersonId,
        lead = lead,
        title = title,
        url = url,
        staffOrg = staffOrg?.toEntity(),
        commissionPositions = commissionPositions.map { it.toEntity(idPerson) }.toSet(),
        fractionPositions = fractionPositions.map { it.toEntity(idPerson) }.toSet(),
    )
}
