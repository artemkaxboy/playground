package com.artemkaxboy.playground.gov.dumagovru.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class StringSetStringSimpleConverter : AttributeConverter<Set<String>, String> {

    private val separator = ","

    override fun convertToDatabaseColumn(list: Set<String>?): String? {
        return list?.filter { it.isNotBlank() }
            ?.joinToString(separator)
    }

    override fun convertToEntityAttribute(joined: String?): Set<String>? {
        return joined?.splitToSequence(separator)
            ?.filter { it.isNotBlank() }
            ?.toSet()
    }
}
