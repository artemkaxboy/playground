package com.artemkaxboy.playground.gov.dumagovru.entity

import com.artemkaxboy.playground.gov.utils.entityEquals
import com.artemkaxboy.playground.gov.utils.entityHashCode
import com.artemkaxboy.playground.gov.utils.entityToString
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class IntGroupPosition(

    @Id
    @Column(nullable = false)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "int_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val intGroup: IntGroup? = null,

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
    title: String = "title",
) = IntGroupPosition(
    id = id,
    intGroup = intGroup,
    title = title,
)
