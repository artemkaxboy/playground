package com.artemkaxboy.leetcode

object LeetUtils {

    fun stringToIntArray(string: String): IntArray {
        return string.trim('[', ']').split(",").mapNotNull { it.toIntOrNull() }.toIntArray()
    }

    fun stringToCharArray(string: String): CharArray {
        return string.trim('[', ']').split(",").map { it[0] }.toCharArray()
    }
}
