package com.artemkaxboy.calculator

/**
 * https://acmp.ru/article.asp?id_text=1329
 */
class Num(private val maxLen: Int) {

    private var positive = true
    private var length = 0
    private val intArray: IntArray = IntArray(maxLen)

    private val elementVolume = 10

    /**
     * Returns length of loaded number
     */
    private fun getLength() = length

    private fun setLength(length: Int) {
        this.length = length
    }

    /**
     * Returns digit [0-9] of the given decade or null if it is out of available range.
     */
    private fun getDigit(decade: Int): Int? = intArray.getOrNull(decade - 1)

    private fun setDigit(decade: Int, digit: Int) = intArray.set(decade - 1, digit)

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
            sum.setDigit(i, s1 % elementVolume)
            overflow = s1 / elementVolume
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

    operator fun times(other: Num): Num {
        val (v1, v2) = if (getLength() > other.getLength()) this to other else other to this

        val v1Length = v1.getLength()
        val v2Length = v2.getLength()
        var sum: Num? = null

        var i = 1
        while (i <= v2Length) {
            val n2 = v2.getDigit(i)!!

            val decadeMultiplication = Num(v1Length + i)
            var j = 1
            var overflow = 0
            while (j <= v1Length) {
                val n1 = v1.getDigit(j)!!
                val m = n1 * n2 + overflow
                decadeMultiplication.setDigit(j + (i - 1), m % elementVolume)
                overflow = m / elementVolume
                j++
            }
            if (overflow == 0) {
                decadeMultiplication.setLength((j - 1) + (i - 1))
            } else {
                decadeMultiplication.setDigit(j + (i - 1), overflow)
                decadeMultiplication.setLength(j + (i - 1))
            }

            sum = sum?.let { it + decadeMultiplication } ?: decadeMultiplication
            i++
        }

        sum?.positive = positive == other.positive
        return sum ?: fromInput("0")
    }

    operator fun div(other: Num): Num {
        var i = getLength()
        // if other longer the answer is 0
        // if other = 10, return subnum
        while (i > 0) {
//            getSubNumber()
//            if () // todo make a copy of this with i decades, check if it is greater than the other
            // if so substract while it is still greater
            // when comes less add next decade to the remain, etc
            println(i)

            i--
        }

        return this
    }

    private fun getSubNumber(startDecade: Int, length: Int): Num {

        intArray.slice(startDecade until (startDecade + length))
        val subnum = Num(length)

        return subnum
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

    /**
     * Change number sign +/-.
     */
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
        return intArray
            .reversed()
            .dropWhile { it == 0 }
            .joinToString("")
            .takeIf { it.isNotEmpty() }
            ?.let { (if (this.positive) "" else "-") + it }
            ?: "0"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Num

        if (length == 0 && other.length == 0) return true
        if (positive != other.positive || length != other.length) return false
        for (i in 1..getLength()) {
            if (getDigit(i) != other.getDigit(i)) return false
        }
        return true
    }

    override fun hashCode(): Int {
        return intArray.contentHashCode()
    }

    private fun read(string: String): IllegalArgumentException? {
        val stringLength = string.length

        if (stringLength > maxLen) {
            return IllegalArgumentException("Input string length exceeded max available length $maxLen")
        } else {
            setLength(stringLength)
            var decade = 1
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

                char.digitToInt().takeIf { it in 0..9 }?.let { setDigit(decade, it) }
                    ?: return IllegalArgumentException("Unknown character $char in input string")
                decade++
                stringIndex--
            }
            setLength(findRealLength())
        }

        return null
    }

    private fun findRealLength(): Int {
        var realLength = intArray.size
        for (i in (realLength) downTo 1) { // length cannot be less than 1 digit, even if it is 0
            if (getDigit(i) == 0) realLength--
            else break
        }
        return realLength
    }

    companion object {

        fun fromInput(input: String): Num {

            return Num(input.length).apply { read(input) }
        }
    }
}
