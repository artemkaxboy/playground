package com.artemkaxboy.calculator

/**
 * https://acmp.ru/article.asp?id_text=1329
 */
class Num(private val maxLen: Int) {

    var positive = true
    private val intArray: IntArray = IntArray(maxLen + 1)

    constructor(maxLen: Int, positive: Boolean, arrayToCopy: IntArray) : this(maxLen) {
        this.positive = positive
        arrayToCopy.copyInto(intArray, 0, 0, maxLen)
    }

    /**
     * Returns length of loaded number
     */
    private fun getLength() = intArray[0]

    private fun setLength(length: Int) {
        intArray[0] = length
    }

    /**
     * Returns digit [0-9] of the given decade or null if it is out of available range.
     */
    fun getDigit(decade: Int): Int? = intArray.getOrNull(decade)

    fun setDigit(decade: Int, digit: Int) = intArray.set(decade, digit)

    fun copy(inverseSign: Boolean = false): Num =
            Num(getLength(), if (inverseSign) !positive else positive, intArray.copyOf())

    operator fun plus(other: Num): Num {
        val maxNumLength = maxOf(getLength(), other.getLength())
        if (positive != other.positive) {
            return if (this.positive) this - (-other)
            else other - (-this)
        }
        val sum = Num(maxNumLength + 1)

        var overflow = 0
        var i = 1
        while (i in 1..maxNumLength) {
            val n1 = this.getDigit(i) ?: 0
            val n2 = other.getDigit(i) ?: 0
            val s1 = n1 + n2 + overflow
            sum.setDigit(i, s1 % 10)
            overflow = s1 / 10
            i++
        }
        if (overflow == 0) {
            sum.setLength(i - 1)
        } else {
            sum.setDigit(i, overflow)
            sum.setLength(i)
        }
        sum.positive = positive

        return sum
    }

    operator fun unaryMinus(): Num {
        return this.copy(true)
    }

    operator fun minus(other: Num): Num {
        val maxNumLength = maxOf(getLength(), other.getLength())
        if (positive != other.positive) {
            return this + (-other)
        }
        if (!this.positive) return (-other) - (-this)
        val difference = Num(maxNumLength)

        
    }

    operator fun compareTo(other: Num): Int {
        val length = getLength()
        length.compareTo(other.getLength()).takeIf { it != 0 }?.let { return it }

        for (decade in 1..length) {
            getDigit(decade)
                    ?.compareTo(requireNotNull(other.getDigit(decade)))
                    ?.takeIf { it != 0 }
                    ?.let { return it }
        }

        return 0
    }

    override fun toString(): String {
        return intArray.drop(1)
                .reversed()
                .joinToString("")
                .dropWhile { it == '0' }
                .ifEmpty { "0" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Num

        return intArray.contentEquals(other.intArray)
    }

    override fun hashCode(): Int {
        return intArray.contentHashCode()
    }

    private fun read(string: String): IllegalArgumentException? {
        val stringLength = string.length

        if (stringLength > maxLen) {
            return IllegalArgumentException("Input string length exceeded max available length $maxLen")
        } else {
            intArray[0] = stringLength
            var arrayIndex = 1
            for (char in string.reversed()) {
                intArray[arrayIndex] = char.digitToInt().takeIf { it in 0..9 }
                        ?: return IllegalArgumentException("Unknown character $char in input string")
                arrayIndex++
            }
        }

        return null
    }

    companion object {

        fun fromInput(input: String): Num {

            return Num(input.length).apply { read(input) }
        }
    }
}
