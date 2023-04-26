package com.artemkaxboy.algo

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CountCharsTest {

    private val testedService = CountChars()

    @ParameterizedTest
    @CsvSource(
        "AAAABBBCCXYZDDDDEEEFFFAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB,A4B3C2XYZD4E3F3A6B28",
        "A,A",
        "AAA,A3",
        ","
    )
    fun rle_passes(input: String?, expected: String?) {
        val actual = testedService.rle(input.orEmpty())

        Assertions.assertThat(actual).isEqualTo(expected.orEmpty())
    }

    @ParameterizedTest
    @CsvSource(
        "AAAABBBCCXYZDDDDEEEFFFdAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "dA",
        "AAAa",
        "!",
    )
    fun rle_throwsOnWrongInput(input: String) {
        Assertions.assertThatThrownBy {
            testedService.rle(input)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
