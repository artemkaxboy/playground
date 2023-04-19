package com.artemkaxboy.leetcode

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

            val result = null //solution.process(data)

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result\n")
        }
    }

    data class Data(
        val input: Any,
        val expected: Any
    )
}
