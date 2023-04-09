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

    operator fun plus(other: Num): Num {
        if (positive != other.positive) {
            return if (this.positive) this - (-other)   // (+a) + (-b) -> (+a) - (+b)
            else other - (-this)                        // (-a) + (+b) -> (+b) - (+a)
        }

        val maxNumLength = maxOf(getLength(), other.getLength())
        val sum = Num(maxNumLength + 1)

        var overflow = 0
        var i = 1
        while (i <= maxNumLength) {
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

    operator fun minus(other: Num): Num {
        if (positive != other.positive) {
            // (+a) - (-b) -> (+a) + (+b)
            // (-a) - (+b) -> (-a) + (-b)
            return this + (-other)
        } // +1 - +100 -> -100 - -1
        if (this.absCompareTo(other) < 0) {
            return (-other) - (-this)
        }
        // from here `this` is always greater than the other

        val maxNumLength = maxOf(getLength(), other.getLength())
        val difference = Num(maxNumLength)

        var overflow = 0
        var i = 1
        while (i <= maxNumLength) {
            val n1 = this.getDigit(i) ?: 0
            val n2 = other.getDigit(i) ?: 0
            val d1 = n1 - n2 + overflow
            difference.setDigit(i, (d1 + 10) % 10)
            overflow = if (d1 < 0) -1 else 0
            i++
        }
        difference.positive = this.positive

        return difference
    }

    operator fun unaryMinus(): Num {
        return this.apply { positive = !positive }
    }

    private fun absCompareTo(other: Num): Int {
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

    operator fun compareTo(other: Num): Int {
        if (positive != other.positive) return if (positive) 1 else -1

        return absCompareTo(other).let { if (positive) it else -it }
    }

    override fun toString(): String {
        return intArray.drop(1)
                .reversed()
                .joinToString("")
                .dropWhile { it == '0' }
                .takeIf { it.length > 0 }
                ?.let { (if (this.positive) "" else "-") + it }
                ?: "0"
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
            var stringIndex = stringLength - 1
            while (stringIndex >= 0) {
                val char = string[stringIndex]

                if (stringIndex == 0) {
                    when (char) {
                        '-' -> {
                            this.positive = false
                            break
                        }

                        '+' ->
                            break
                    }
                }

                intArray[arrayIndex] = char.digitToInt().takeIf { it in 0..9 }
                        ?: return IllegalArgumentException("Unknown character $char in input string")
                arrayIndex++
                stringIndex--
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
