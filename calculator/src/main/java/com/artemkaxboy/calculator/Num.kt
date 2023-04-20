package com.artemkaxboy.calculator

/**
 * https://acmp.ru/article.asp?id_text=1329
 */
class Num(private val maxLen: Int) {

    private var positive = true
    private val negative
        get() = !positive
    private val data: IntArray = IntArray(maxLen) // asList asReversed use the same memory

    /**
     * Copy long number with optional shift.
     *
     * ```
     * 123.copy(shiftLeft = 2) = 12300
     * [3, 2, 1] => [0, 0, 3, 2, 1]
     * ```
     *
     * @param shiftLeft number of decades to shift
     *
     * @throws IllegalArgumentException when [shiftLeft] is negative
     *
     * @return new shifted [Num]
     */
    private fun copy(shiftLeft: Int = 0): Num {
        require(shiftLeft >= 0) { "Shift must not be negative" }
        val srcLength = getSignificantElementsCount()
        val newNum = Num(srcLength + shiftLeft)
        data.copyInto(newNum.data, shiftLeft, 0, srcLength)
        newNum.positive = positive

        return newNum
    }

    /**
     * Count non-significant leading zeros.
     * ```
     * 000123 = 3
     * 001230 = 2
     * ```
     */
    private fun getNonSignificantLeadingDecades() = data.asList().asReversed().takeWhile { it == 0 }.count()

    /**
     * Count significant decades
     */
    private fun getSignificantElementsCount() = data.size - getNonSignificantLeadingDecades()

    /**
     * Returns value of element at position. For decimal 1 is first decade, 2 - second, etc.
     */
    private fun getElement(position: Int, defaultValue: Int = defaultElementValue): Int =
        data.getOrNull(position - 1) ?: defaultValue

    private fun setElement(position: Int, value: Int) = data.set(position - 1, value)

    /**
     * Creates new Num = `this` + `other`
     */
    operator fun plus(other: Num): Num {
        if (this.positive && other.negative) return this - (-other) //  a+(-b) >>> a-b
        if (this.negative && other.positive) return other - (-this) //  -a+b   >>> b-a

        val maxElementsCount = maxOf(getSignificantElementsCount(), other.getSignificantElementsCount())
        val result = Num(maxElementsCount + 1)

        var overflow = defaultElementValue
        var i = 1
        while (i <= maxElementsCount) {
            val v1 = this.getElement(i)
            val v2 = other.getElement(i)
            val r = v1 + v2 + overflow
            result.setElement(i, r % elementVolume)
            overflow = r / elementVolume
            i++
        }
        if (overflow != defaultElementValue) {
            result.setElement(i, overflow)
        }
        result.positive = positive

        return result
    }

    /**
     * Creates new Num = `this` * `other`
     */
    operator fun times(other: Num): Num {
        val (v1, v2) = if (getSignificantElementsCount() > other.getSignificantElementsCount()) this to other else other to this

        val v1Length = v1.getSignificantElementsCount()
        val v2Length = v2.getSignificantElementsCount()
        val resultComponents = ArrayList<Num>(v2Length)

        var i = 1
        while (i <= v2Length) {
            val n2 = v2.getElement(i)

            val decadeMultiplication = Num(v1Length + i)
            var j = 1
            var overflow = defaultElementValue
            while (j <= v1Length) {
                val n1 = v1.getElement(j)
                val m = n1 * n2 + overflow
                decadeMultiplication.setElement(j + (i - 1), m % elementVolume)
                overflow = m / elementVolume
                j++
            }
            if (overflow != defaultElementValue) {
                decadeMultiplication.setElement(j + (i - 1), overflow)
            }

            resultComponents.add(decadeMultiplication)
            i++
        }

        return resultComponents.reduceOrNull { acc, num -> acc + num }
            ?.also { it.positive = positive == other.positive }
            ?: fromInput("0")
    }

    operator fun div(other: Num): Num {
        val v1Length = getSignificantElementsCount()
        val v2Length = other.getSignificantElementsCount()
        // todo: optimise if other = 10, return subnum
        if (v2Length > v1Length) return fromInput("0")
        var shift = v1Length - v2Length
        val stringBuilder = StringBuilder()
        var remain = copy().apply { positive = true }
        while (shift >= 0) {
            val v2Shifted = other.copy(shift).apply { positive = true }

            var i = 0
            while (remain >= v2Shifted) {
                remain -= v2Shifted
                i++
            }
            stringBuilder.append(i)

            shift--
        }

        val sign = if (positive != other.positive) "-" else ""
        return fromInput(sign + stringBuilder.toString())
    }

    operator fun minus(other: Num): Num {
        if (positive != other.positive) {
            //  a-(-b) >>> a+b
            //  (-a)-b >>> (-a)+(-b)
            return this + (-other)
        }
        if (this.absCompareTo(other) < 0) {
            // 1-100 >>> -100-(-1)
            return (-other) - (-this)
        }
        // from here `this` is always greater than the other

        val maxNumLength = maxOf(getSignificantElementsCount(), other.getSignificantElementsCount())
        val result = Num(maxNumLength)

        var overflow = defaultElementValue
        var i = 1
        while (i <= maxNumLength) {
            val v1 = getElement(i)
            val v2 = other.getElement(i)
            val r = v1 - v2 + overflow
            result.setElement(i, (r + elementVolume) % elementVolume)
            overflow = if (r < 0) -1 else 0
            i++
        }
        result.positive = this.positive

        return result
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
        val length = getSignificantElementsCount()
        length.compareTo(other.getSignificantElementsCount())
            .takeIf { it != 0 }
            ?.let { return it }

        for (element in length downTo 1) {
            getElement(element)
                .compareTo(other.getElement(element))
                .takeIf { it != 0 }
                ?.let { return it }
        }

        return 0
    }

    operator fun compareTo(other: Num): Int {
        if (positive != other.positive) return if (positive) 1 else -1

        return absCompareTo(other).let { if (positive) it else -it }
    }

    override fun toString(): String {
        return data
            .asList()
            .asReversed()
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

        val length = getSignificantElementsCount()
        val otherLength = other.getSignificantElementsCount()
        if (length == 0 && otherLength == 0) return true
        if (positive != other.positive || length != otherLength) return false
        for (i in 1..getSignificantElementsCount()) {
            if (getElement(i) != other.getElement(i)) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = positive.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    private fun read(string: String): IllegalArgumentException? {
        val stringLength = string.length

        if (stringLength > maxLen) {
            return IllegalArgumentException("Input string length exceeded max available length $maxLen")
        } else {
            var element = 1
            var stringIndex = stringLength - 1
            while (stringIndex >= 0) {
                val char = string[stringIndex]

                if (stringIndex == 0) {
                    when (char) {
                        '-' -> {
                            positive = false
                            break
                        }

                        '+' ->
                            break
                    }
                }

                char.digitToInt().takeIf { it in 0..9 }?.let { setElement(element, it) }
                    ?: return IllegalArgumentException("Unknown character $char in input string")
                element++
                stringIndex--
            }
        }

        return null
    }

    companion object {

        private const val elementVolume = 10
        private const val defaultElementValue = 0

        fun fromInput(input: String): Num {

            return Num(input.length).apply { read(input) }
        }
    }
}
