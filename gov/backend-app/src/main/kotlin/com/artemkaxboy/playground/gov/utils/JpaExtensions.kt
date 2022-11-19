package com.artemkaxboy.playground.gov.utils

import org.hibernate.Hibernate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

fun KClass<out Any>.filterFields(filter: (String) -> Boolean): List<KProperty1<out Any, *>> {
    return this.declaredMemberProperties
        .filter { field ->
            field.javaField
                ?.declaredAnnotations
                ?.mapNotNull { annotation -> annotation.annotationClass.simpleName }
                ?.any(filter) ?: false
        }
}

inline fun <reified T : Any> entityHashCode(predicate: () -> T): Int =
    predicate()::class.hashCode()

inline fun entityEquals(predicate: () -> Pair<Any, Any?>): Boolean {
    val (entity, other) = predicate()

    if (entity === other) return true
    if (other == null || Hibernate.getClass(entity) != Hibernate.getClass(other)) return false

    val entityClass = entity::class

    return entityClass.filterFields { it == "Id" }
        .map { it.getter }
        .all { it.call(entity) == it.call(other) }
}

inline fun entityToString(predicate: () -> Any): String {
    val entity = predicate()

    val entityClass = entity::class

    val values = entityClass
        .filterFields { !it.endsWith("ToOne") and !it.endsWith("ToMany") }
        .map { it.name + " = " + it.getter.call(entity) }

    return getClassName(entityClass) + "(" + values.joinToString() + ")"
}

fun getClassName(clazz: KClass<*>): String {
    return clazz.simpleName ?: clazz.qualifiedName ?: "Unknown"
}
