package com.artemkaxboy.leetcode

import kotlin.system.measureTimeMillis

class Leet {

    class Solution {
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("", "")
            doWork(testCase1)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = ""
//                result = solution.run(data.input)
            }

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result")
            println("Time:     $time\n")

            if (data.expected != result) {
                throw AssertionError("\nExpected: ${data.expected}\nActual:   $result")
            }
        }
    }

    data class Data(
        val input: Any,
        val expected: Any
    )
}
