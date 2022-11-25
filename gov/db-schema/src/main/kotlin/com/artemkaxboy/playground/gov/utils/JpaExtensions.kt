package com.artemkaxboy.playground.gov.utils

import org.hibernate.Hibernate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

object JpaExtensions {

    /**
     * Gets all fields with annotations which meet the condition.
     */
    inline fun KClass<out Any>.getFieldsByAnnotation(filter: (String) -> Boolean): List<KProperty1<out Any, *>> {
        return this.declaredMemberProperties
            .filter { field ->
                field.javaField
                    ?.declaredAnnotations
                    ?.mapNotNull { annotation -> annotation.annotationClass.simpleName }
                    ?.any(filter) ?: false
            }
    }

    /**
     * Gets all fields with annotations which does not meet the condition.
     */
    inline fun KClass<out Any>.getFieldsSkipByAnnotation(filter: (String) -> Boolean): List<KProperty1<out Any, *>> {
        return this.declaredMemberProperties
            .filter { field ->
                field.javaField
                    ?.declaredAnnotations
                    ?.mapNotNull { annotation -> annotation.annotationClass.simpleName }
                    ?.none(filter) ?: false
            }
    }

    inline fun <reified T : Any> entityHashCode(predicate: () -> T): Int =
        predicate()::class.hashCode()

    inline fun entityEquals(predicate: () -> Pair<Any, Any?>): Boolean {
        val (entity, other) = predicate()

        if (entity === other) return true
        if (other == null || Hibernate.getClass(entity) != Hibernate.getClass(other)) return false

        val entityClass = entity::class

        return entityClass.getFieldsByAnnotation { it == "Id" }
            .map { it.getter }
            .all { it.call(entity) == it.call(other) }
    }

    inline fun entityToString(predicate: () -> Any): String {
        val entity = predicate()

        val entityClass = entity::class

        val values = entityClass
            .getFieldsSkipByAnnotation {
                it.endsWith("ToOne") || it.endsWith("ToMany")
            }
            .map { it.name + " = " + it.getter.call(entity) }

        return getClassName(entityClass) + "(" + values.joinToString() + ")"
    }

    fun getClassName(clazz: KClass<*>): String {
        return clazz.simpleName ?: clazz.qualifiedName ?: "Unknown"
    }
}
