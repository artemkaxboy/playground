package com.artemkaxboy.leetcode.p06

import java.util.concurrent.ArrayBlockingQueue
import kotlin.system.measureTimeMillis

class Leet649 {

    class Solution {
        val answer = listOf("Radiant", "Dire")

        fun predictPartyVictory(senate: String): String {

            val a1 = CharArray(senate.length)
            val senateList = ArrayBlockingQueue<Char>(senate.length)
            for (i in senate) {
                senateList.add(i)
            }

            for (i in senateList) {
                println(i)
            }

            return answer[0]
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            val testCase1 = Data("RDDRDRDRRDRDRDDRDRDR", "Radiant")
            val testCase1 = Data("12345678", "Radiant")
            doWork(testCase1)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = solution.predictPartyVictory(data.input)
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
        val input: String,
        val expected: String
    )
}
