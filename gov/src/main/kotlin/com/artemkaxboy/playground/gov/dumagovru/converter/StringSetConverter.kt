package com.artemkaxboy.playground.gov.dumagovru.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

const val SEPARATOR = ",,,"

@Converter
class StringSetConverter : AttributeConverter<Set<String>, String> {

    override fun convertToDatabaseColumn(list: Set<String>?): String? {
        return list?.joinToString(SEPARATOR)
    }

    override fun convertToEntityAttribute(joined: String?): Set<String>? {
        return joined?.split(SEPARATOR)?.toSet()
    }
}
