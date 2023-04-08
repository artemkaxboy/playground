package com.artemkaxboy.calculator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NumTest {

    @Test
    fun readAllDigits_passes() {
        val expected = "1234567890"

        val num = Num.fromInput(expected)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun toString_dropsLeadingZeros() {
        val input = "00000001"
        val expected = "1"

        val num = Num.fromInput(input)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun toString_leavesLastZero() {
        val input = "00000000"
        val expected = "0"

        val num = Num.fromInput(input)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun readLong_passes() {
        val expected = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val num = Num.fromInput(expected)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun readLong_whenEmptyString() {
        val input = ""
        val expected = "0"

        val num = Num.fromInput(input)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun readLong_whenUnknownChar() {
        val invalidInput = "123asdf123"

        Assertions.assertThrows(IllegalArgumentException::class.java) { Num.fromInput(invalidInput) }
    }

    @Test
    fun compare_equals() {
        val s1 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        val s2 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val num1 = Num.fromInput(s1)
        val num2 = Num.fromInput(s2)
        val actual1 = num1.compareTo(num2)
        val actual2 = num2.compareTo(num1)

        Assertions.assertEquals(actual1, actual2)
        Assertions.assertEquals(actual1, 0)
        Assertions.assertEquals(num1, num2)
    }

    @Test
    fun compare_less() {
        val s1 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        val s2 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999998"

        val num1 = Num.fromInput(s1)
        val num2 = Num.fromInput(s2)
        val actual1 = num1.compareTo(num2)
        val actual2 = num2.compareTo(num1)

        Assertions.assertTrue(actual1 > 0)
        Assertions.assertTrue(actual2 < 0)
    }

    @Test
    fun compare_less2() {
        val s1 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        val s2 = "999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val num1 = Num.fromInput(s1)
        val num2 = Num.fromInput(s2)
        val actual1 = num1.compareTo(num2)
        val actual2 = num2.compareTo(num1)

        Assertions.assertTrue(actual1 > 0)
        Assertions.assertTrue(actual2 < 0)
    }

    @Test
    fun compare_greater() {
        val s1 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999998"
        val s2 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val num1 = Num.fromInput(s1)
        val num2 = Num.fromInput(s2)
        val actual1 = num1.compareTo(num2)
        val actual2 = num2.compareTo(num1)

        Assertions.assertTrue(actual1 < 0)
        Assertions.assertTrue(actual2 > 0)
    }

    @Test
    fun compare_greater2() {
        val s1 = "999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        val s2 = "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"

        val num1 = Num.fromInput(s1)
        val num2 = Num.fromInput(s2)
        val actual1 = num1.compareTo(num2)
        val actual2 = num2.compareTo(num1)

        Assertions.assertTrue(actual1 < 0)
        Assertions.assertTrue(actual2 > 0)
    }

    @Test
    fun plus_passes() {
        val expected = "44400000000000000000000000000000000000000000000"
        val v1 = Num.fromInput("12300000000000000000000000000000000000000000000")
        val v2 = Num.fromInput("32100000000000000000000000000000000000000000000")

        val sum = v1 + v2

        Assertions.assertEquals(expected, sum.toString())
    }
}
