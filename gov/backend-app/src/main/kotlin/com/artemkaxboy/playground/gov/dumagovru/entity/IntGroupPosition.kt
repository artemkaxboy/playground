package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class IntGroupPosition(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_group_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroup: IntGroup? = null,

    @Column(nullable = false, name = "int_group_id")
    val intGroupId : Long? = intGroup?.id,

    @Column(columnDefinition = "TEXT", nullable = false)
    val title: String = "",
) {

    override fun equals(other: Any?) = entityEquals { this to other }
    override fun hashCode() = entityHashCode { this }
    override fun toString() = entityToString { this }
}

fun makeIntGroupPosition(
    id: Long = 1L,
    intGroup: IntGroup? = makeIntGroup(),
    intGroupId: Long = intGroup!!.id!!,
    title: String = "title",
) = IntGroupPosition(
    id = id,
    intGroup = intGroup,
    intGroupId = intGroupId,
    title = title,
)
