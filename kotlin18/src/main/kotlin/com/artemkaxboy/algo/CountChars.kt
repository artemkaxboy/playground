package com.artemkaxboy.algo

import java.lang.StringBuilder

class CountChars {

    fun rle(input: String): String {
        val size = input.length
        val sb = StringBuilder()

        var i = 0
        var last: Char? = null
        var repeat = 1
        while (i < size) {
            val char = input[i]

            if (char < 'A' || char > 'Z') throw IllegalArgumentException("Illegal char $char at position $i")

            if (last != char) {
                if (last != null && repeat > 1) sb.append(repeat)
                sb.append(char)
                repeat = 1
            } else {
                repeat++
            }

            last = char
            i++
        }
        if (repeat > 1) {
            sb.append(repeat)
        }

        return sb.toString()
    }
}
