package com.artemkaxboy.calculator

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class NumTest {

    @ParameterizedTest
    @CsvSource(
        "200,100", "200,-100", "100,-200", "-100,-200", // same length
        "100, 50", "100,-50", "50,-100", "-50,-100", // dif length
        "100,0", "0,-100", // zero
    )
    fun compare_firstGreater(s1: String, s2: String) {
        val v1 = Num.fromInput(s1)
        val v2 = Num.fromInput(s2)

        Assertions.assertTrue(v1 > v2)
    }

    @ParameterizedTest
    @CsvSource(
        "100,200", "-100,200", "-200,100", "-200,-100", // same length
        "50,100", "-50,100", "-100,50", "-100,-50", // dif length
        "0,100", "-100,0",
    )
    fun compare_firstLess(s1: String, s2: String) {
        val v1 = Num.fromInput(s1)
        val v2 = Num.fromInput(s2)

        Assertions.assertTrue(v1 < v2)
    }

    @ParameterizedTest
    @CsvSource(
        "100,100", "0,0", "-0,0",
    )
    fun compare_equals(s1: String, s2: String) {
        val v1 = Num.fromInput(s1)
        val v2 = Num.fromInput(s2)

        Assertions.assertTrue(v1 == v2)
    }

    @ParameterizedTest
    @CsvSource(
        "100,200,-100", "-100,200,-300", "100,-200,300", "-100,-200,100",
        "100,50,50", "-100,50,-150", "100,-50,150", "-100,-50,-50",
        "100,100,0", "-100,-100,0",
        "100,0,100", "-100,0,-100", "100,-0,100", "-100,-0,-100",
        "0,100,-100", "-0,100,-100", "0,-100,100", "-0,-100,100",
        "19998,9999,9999",
    )
    fun difference(s1: String, s2: String, expected: String) {
        val v1 = Num.fromInput(s1)
        val v2 = Num.fromInput(s2)

        val actual = v1 - v2

        Assertions.assertEquals(expected, actual.toString())
    }

    @ParameterizedTest
    @CsvSource(
        "100,200,300", "-100,200,100", "100,-200,-100", "-100,-200,-300",
        "100,50,150", "-100,50,-50", "100,-50,50", "-100,-50,-150",
        "100,100,200", "-100,-100,-200",
        "100,0,100", "-100,0,-100", "100,-0,100", "-100,-0,-100",
        "0,100,100", "-0,100,100", "0,-100,-100", "-0,-100,-100",
        "9999,9999,19998",
    )
    fun sum(s1: String, s2: String, expected: String) {
        val v1 = Num.fromInput(s1)
        val v2 = Num.fromInput(s2)

        val actual = v1 + v2

        Assertions.assertEquals(expected, actual.toString())
    }

    @ParameterizedTest
    @CsvSource(
        "+1234567890,1234567890",
        "-1234567890,-1234567890",
        "000000000000000000000000000000000000000000000000000000,0",
        "+000000000000000000000000000000000000000000000000000000,0",
        "-000000000000000000000000000000000000000000000000000000,0",
        "0000000000000000000000000000000000000000000000000000001,1",
        "+0000000000000000000000000000000000000000000000000000001,1",
        "-0000000000000000000000000000000000000000000000000000001,-1",
        "000000000000000000000000000000000000000000000000000000100,100",
        "+000000000000000000000000000000000000000000000000000000100,100",
        "-000000000000000000000000000000000000000000000000000000100,-100",
        "999999999999999999999999999999999999999999999999999999,999999999999999999999999999999999999999999999999999999",
        "-99999999999999999999999999999999999999999999999999999,-99999999999999999999999999999999999999999999999999999",
    )
    fun read_passes(input: String, expected: String) {
        val v = Num.fromInput(input)
        val actual = v.toString()

        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @CsvSource(
        "f",
        "1111f1111",
        "++1", "1+", "1+1",
        "--1", "1-", "1-1",
        "+-1", "-+1",
    )
    fun read_fails(input: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) { Num.fromInput(input) }
    }

    @Test
    fun readLong_whenEmptyString() {
        val input = ""
        val expected = "0"

        val num = Num.fromInput(input)
        val actual = num.toString()

        Assertions.assertEquals(expected, actual)
    }
}
