package com.artemkaxboy.playground.gov.dumagovru.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class LongSetConverter : AttributeConverter<Set<Long>, String> {

    override fun convertToDatabaseColumn(list: Set<Long>?): String? {
        return list?.joinToString(SEPARATOR)
    }

    override fun convertToEntityAttribute(joined: String?): Set<Long>? {
        return joined?.splitToSequence(SEPARATOR)
            ?.map { it.toLongOrNull() }
            ?.filterNotNull()
            ?.toSet()
    }
}
