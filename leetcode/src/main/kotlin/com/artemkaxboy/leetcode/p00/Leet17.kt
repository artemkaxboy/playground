package com.artemkaxboy.leetcode.p00

/**
 * Runtime  180ms   Beats   48.85%
 * Memory   36.5MB  Beats   43.93%
 */
class Leet17 {
    private val letters = mapOf(
        '2' to "abc",
        '3' to "def",
        '4' to "ghi",
        '5' to "jkl",
        '6' to "mno",
        '7' to "pqrs",
        '8' to "tuv",
        '9' to "wxyz"
    )

    fun letterCombinations(digits: String): List<String> {
        // println(digits)

        val output = getAllLetters(listOf(""), digits).filter { it.isNotEmpty() }
        //println(output)
        return output
    }

    private fun getAllLetters(prefixes: List<String>, digits: String): List<String> {
        if (digits.isEmpty()) return prefixes

        // println("prefs: $prefixes\ndigits: $digits\n\n")

        val newPrefixes = prefixes.flatMap { prefix ->
            letters[digits[0]!!]!!.map { letter ->
                "$prefix$letter"
            }
        }
        return getAllLetters(newPrefixes, digits.drop(1))
    }
}
