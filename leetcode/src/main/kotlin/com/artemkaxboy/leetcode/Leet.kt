package com.artemkaxboy.leetcode

class Leet {

    class Solution {
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "" to ""
            doWork(testCase1)
        }

        private fun doWork(data: Pair<Any, Any>) {
            val solution = Solution()

            val result = "comment me"
//            val result = solution.process(data)

            println("Data:     ${data.first}")
            println("Expected: ${data.second}")
            println("Result:   $result\n")
        }

    }
}
