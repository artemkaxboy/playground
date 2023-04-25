package com.artemkaxboy.leetcode

object LeetUtils {

    @Deprecated("Use String.toIntArray() instead")
    fun stringToIntArray(string: String): IntArray {
        return string.trim('[', ']').split(",").mapNotNull { it.toIntOrNull() }.toIntArray()
    }

    @Deprecated("Use String.toCharArray() instead")
    fun stringToCharArray(string: String): CharArray {
        return string.trim('[', ']').split(",").map { it[0] }.toCharArray()
    }

    fun String.toIntArray(): IntArray {
        return stringToIntArray(this)
    }

    fun String.toIntList(): List<Int> {
        return toIntArray().asList()
    }

    fun String.toCharArray(): CharArray {
        return stringToCharArray(this)
    }
}
