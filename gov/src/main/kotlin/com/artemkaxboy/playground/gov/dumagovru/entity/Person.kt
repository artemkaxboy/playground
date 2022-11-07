package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.dumagovru.dto.PersonDto
import org.hibernate.Hibernate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Person(

    @Id
    val id: Long,

//    @Column(name = "commission_positions")
//    val commissionPositions: Set<CommissionPositionDto>,

    @Column(name = "first_name", columnDefinition = "TEXT")
    val firstName: String,

    @Column(name = "second_name", columnDefinition = "TEXT")
    val secondName: String,

    @Column(name = "last_name", columnDefinition = "TEXT")
    val lastName: String,

    @Column(columnDefinition = "TEXT")
    val photo: String?,

    @Column(name = "original_ais_person_id")
    val originalAisPersonId: Long?,

    @Column(columnDefinition = "TEXT")
    val lead: String,

    @Column(columnDefinition = "TEXT")
    val title: String,

    @Column(columnDefinition = "TEXT")
    val url: String,

    @JoinColumn(name = "staff_org")
    @ManyToOne(cascade = [CascadeType.ALL])
    val staffOrg: StaffOrg?,

    @Column(name = "fraction_position")
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
    val person = Person(
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
    )
    val fractionPositions = fractionPositions.map { it.toEntity(person) }.toSet()
    return person.copy(fractionPositions = fractionPositions)
}
