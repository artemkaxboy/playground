package com.artemkaxboy.leetcode.p02

import com.artemkaxboy.leetcode.LeetUtils

/**
 * Runtime:     641ms       Beats:   13.38%
 * Memory:      49.3MB      Beats:   57.96%
 */
class Leet215 {

    class Solution {

        fun findKthLargest(nums: IntArray, k: Int): Int {
            val list = nums.asList().sortedDescending()
            return list[k - 1]
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("[3,2,1,5,6,4]", 2, 5)
            doWork(testCase1)

            val testCase2 = Data("[3,2,3,1,2,4,5,5,6]", 4, 4)
            doWork(testCase2)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result = solution.findKthLargest(LeetUtils.stringToIntArray(data.input), data.largest)

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result\n")
        }
    }

    data class Data(
        val input: String,
        val largest: Int,
        val expected: Int,
    )
}
