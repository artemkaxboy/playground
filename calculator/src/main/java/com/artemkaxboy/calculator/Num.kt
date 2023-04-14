package com.artemkaxboy.calculator

/**
 * https://acmp.ru/article.asp?id_text=1329
 */
class Num(private val maxLen: Int) {

    private var positive = true
    private val negative
        get() = !positive
    private val intArray: IntArray = IntArray(maxLen) // asList asReversed use the same memory

    private val elementVolume = 10

    private fun copy(shiftLeft: Int = 0): Num {
        require(shiftLeft >= 0) { "Shift must not be negative" }
        val srcLength = getElementsCount()
        val newNum = Num(srcLength + shiftLeft)
        intArray.copyInto(newNum.intArray, shiftLeft, 0, srcLength)
        newNum.positive = positive

        return newNum
    }

    /**
     * Returns length of loaded number
     */
    private fun getElementsCount() = intArray.size - intArray.asList().asReversed().takeWhile { it == 0 }.count()

    /**
     * Returns digit [0-9] of the given decade or null if it is out of available range.
     */
    private fun getElement(decade: Int): Int? = intArray.getOrNull(decade - 1)

    private fun setElement(decade: Int, digit: Int) = intArray.set(decade - 1, digit)

    /**
     * Creates new Num = `this` + `other`
     */
    operator fun plus(other: Num): Num {
        if (this.positive && other.negative) return this - (-other) //  a+(-b) >>> a-b
        if (this.negative && other.positive) return other - (-this) //  -a+b   >>> b-a

        val maxElementsCount = maxOf(getElementsCount(), other.getElementsCount())
        val sum = Num(maxElementsCount + 1)

        var overflow = 0
        var i = 1
        while (i <= maxElementsCount) {
            val v1 = this.getElement(i) ?: 0
            val v2 = other.getElement(i) ?: 0
            val r = v1 + v2 + overflow
            sum.setElement(i, r % elementVolume)
            overflow = r / elementVolume
            i++
        }
        if (overflow != 0) {
            sum.setElement(i, overflow)
        }
        sum.positive = positive

        return sum.copy()
    }

    /**
     * Creates new Num = `this` * `other`
     */
    operator fun times(other: Num): Num {
        val (v1, v2) = if (getElementsCount() > other.getElementsCount()) this to other else other to this

        val v1Length = v1.getElementsCount()
        val v2Length = v2.getElementsCount()
        var sum: Num? = null

        var i = 1
        while (i <= v2Length) {
            val n2 = v2.getElement(i)!!

            val decadeMultiplication = Num(v1Length + i)
            var j = 1
            var overflow = 0
            while (j <= v1Length) {
                val n1 = v1.getElement(j)!!
                val m = n1 * n2 + overflow
                decadeMultiplication.setElement(j + (i - 1), m % elementVolume)
                overflow = m / elementVolume
                j++
            }
            if (overflow != 0) {
                decadeMultiplication.setElement(j + (i - 1), overflow)
            }

            sum = sum?.let { it + decadeMultiplication } ?: decadeMultiplication
            i++
        }

        sum?.positive = positive == other.positive
        return sum ?: fromInput("0")
    }

    operator fun div(other: Num): Num {
//        999999999999999999 / 9
//        900000000000000000
        val v1Length = getElementsCount()
        val v2Length = other.getElementsCount()
        // if other longer the answer is 0
        // if other = 10, return subnum
        if (v2Length > v1Length) return fromInput("0")
        var shift = v1Length - v2Length
        val stringBuilder = StringBuilder()
        var remain = copy().apply { positive = true }
        while (shift >= 0) {
            val v2Shifted = other.copy(shift).apply { positive = true }
//            getSubNumber()
//            if () // todo make a copy of this with i decades, check if it is greater than the other
            // if so substract while it is still greater
            // when comes less add next decade to the remain, etc
//            println(shift)

            var i = 0
            while (remain >= v2Shifted) {
                remain = remain - v2Shifted
                i++
            }
            stringBuilder.append(i)

            shift--
        }

        val sign = if (this.positive != other.positive) "-" else ""
        return fromInput(sign + stringBuilder.toString())
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

        val maxNumLength = maxOf(getElementsCount(), other.getElementsCount())
        val difference = Num(maxNumLength)

        var overflow = 0
        var i = 1
        while (i <= maxNumLength) {
            val n1 = this.getElement(i) ?: 0
            val n2 = other.getElement(i) ?: 0
            val d1 = n1 - n2 + overflow
            difference.setElement(i, (d1 + 10) % 10)
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
        return copy().apply { invert() }
    }

    private fun invert() {
        positive = !positive
    }

    private fun absCompareTo(other: Num): Int {
        val length = getElementsCount()
        length.compareTo(other.getElementsCount()).takeIf { it != 0 }?.let { return it }

        for (decade in length downTo 1) {
            getElement(decade)
                ?.compareTo(requireNotNull(other.getElement(decade)))
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

        val length = getElementsCount()
        val otherLength = other.getElementsCount()
        if (length == 0 && otherLength == 0) return true
        if (positive != other.positive || length != otherLength) return false
        for (i in 1..getElementsCount()) {
            if (getElement(i) != other.getElement(i)) return false
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

                char.digitToInt().takeIf { it in 0..9 }?.let { setElement(decade, it) }
                    ?: return IllegalArgumentException("Unknown character $char in input string")
                decade++
                stringIndex--
            }
        }

        return null
    }

    private fun findRealLength(): Int {
        var realLength = intArray.size
        for (i in (realLength) downTo 1) { // length cannot be less than 1 digit, even if it is 0
            if (getElement(i) == 0) realLength--
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
