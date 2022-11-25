package com.artemkaxboy.playground.gov.utils

import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityEquals
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityHashCode
import com.artemkaxboy.playground.gov.utils.JpaExtensions.entityToString
import com.artemkaxboy.playground.gov.utils.JpaExtensions.getClassName
import com.artemkaxboy.playground.gov.utils.JpaExtensions.getFieldsByAnnotation
import com.artemkaxboy.playground.gov.utils.JpaExtensions.getFieldsSkipByAnnotation
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.annotations.NotNull
import org.junit.jupiter.api.Test
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

internal class JpaExtensionsTest {

    @Test
    fun getFieldsByAnnotation_passes() {
        val fields = TestEntity::class.getFieldsByAnnotation { it == "Id" }
        assertThat(fields)
            .containsExactlyInAnyOrder(TestEntity::id1, TestEntity::id2)
    }

    @Test
    fun getFieldsSkipByAnnotation_passes() {
        val fields = TestEntity::class.getFieldsSkipByAnnotation {
            it.endsWith("ToOne") || it.endsWith("ToMany")
        }
        assertThat(fields)
            .containsExactlyInAnyOrder(TestEntity::id1, TestEntity::id2, TestEntity::field)
    }

    @Test
    fun entityEquals_whenCompareDifferentIds() {
        val e1 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm1", "f1")
        val e2 = TestEntity(2, "i1", "oto1", "otm1", "mto1", "mtm1", "f1")
        val e3 = TestEntity(1, "i2", "oto1", "otm1", "mto1", "mtm1", "f1")

        assertThat(e1).isNotEqualTo(e2).isNotEqualTo(e3)
        assertThat(e2).isNotEqualTo(e3)
    }

    @Test
    fun entityEquals_whenCompareEqualIds() {
        val e1 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm1", "f1")
        val e2 = TestEntity(1, "i1", "oto2", "otm1", "mto1", "mtm1", "f1")
        val e3 = TestEntity(1, "i1", "oto1", "otm2", "mto1", "mtm1", "f1")
        val e4 = TestEntity(1, "i1", "oto1", "otm1", "mto2", "mtm1", "f1")
        val e5 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm2", "f1")
        val e6 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm1", "f2")

        assertThat(e1).isEqualTo(e2).isEqualTo(e3).isEqualTo(e4).isEqualTo(e5).isEqualTo(e6)
        assertThat(e2).isEqualTo(e3).isEqualTo(e4).isEqualTo(e5).isEqualTo(e6)
        assertThat(e3).isEqualTo(e4).isEqualTo(e5).isEqualTo(e6)
        assertThat(e4).isEqualTo(e5).isEqualTo(e6)
        assertThat(e5).isEqualTo(e6)
    }

    @Test
    fun entityToString() {
        val e1 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm1", "f1")

        assertThat(e1.toString())
            .contains("id1 = 1")
            .contains("id2 = i1")
            .contains("field = f1")
    }

    @Test
    fun getClassName_passes() {
        val e1 = TestEntity(1, "i1", "oto1", "otm1", "mto1", "mtm1", "f1")

        assertThat(getClassName(e1::class)).isEqualTo("TestEntity")
    }

    data class TestEntity(
        @javax.persistence.Id
        val id1: Long = 1,

        @org.springframework.data.annotation.Id
        val id2: String = "name",

        @NotNull
        @OneToOne
        val oneToOne: String = "oneToOne",

        @Column(nullable = false)
        @OneToMany
        val oneToMany: String = "oneToMany",

        @ManyToOne
        val manyToOne: String = "manyToOne",

        @ManyToOne
        val manyToMany: String = "manyToMany",
        val field: String = "field",
    ) {

        override fun equals(other: Any?) = entityEquals { this to other }
        override fun hashCode() = entityHashCode { this }
        override fun toString() = entityToString { this }
    }
}
