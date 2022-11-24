package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
@IdClass(PersonToStaffOrg.IdClass::class)
data class PersonToStaffOrg(

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val person: Person? = null,

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "staff_org_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val staffOrg: StaffOrg? = null,
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }

    data class IdClass(
        val person: Long? = null,
        val staffOrg: Long? = null,
    ) : Serializable
}

fun makePersonToStaffOrg(
    person: Person? = makePerson(),
    personId: Long = person!!.id!!,
    staffOrg: StaffOrg? = makeStaffOrg(),
    staffOrgId: Long = staffOrg!!.id!!,
) = PersonToStaffOrg(
    person = person,
//        personId = personId,
    staffOrg = staffOrg,
//        staffOrgId = staffOrgId,
)
