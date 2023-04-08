package com.artemkaxboy.leetcode.unfinished

import com.artemkaxboy.leetcode.LeetUtils
import java.util.*

class Leet41 {

    fun firstMissingPositive(nums: IntArray): Int {
        val max = nums.max() ?: 0
        val bitSet = BitSet(max)

        for (i in nums.indices) {
            if (nums[i] > 0) {
                bitSet.set(nums[i] - 1)
            }
        }

        return bitSet.nextClearBit(0) + 1
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "[1,2,0]" to 3
            doWork(testCase1)

            val testCase2 = "[3,4,-1,1]" to 2
            doWork(testCase2)

            val testCase3 = "[7,8,9,11,12]" to 1
            doWork(testCase3)

            val myCase = "[]" to 1
            doWork(myCase)
        }

        private fun doWork(data: Pair<String, Int>) {
            val input = data.first
            val expected = data.second
            val solution = Leet41()

            val result = solution.firstMissingPositive(LeetUtils.stringToIntArray(input))

            println("    Data: $input")
            println("  Result: $result")
            println("Expected: $expected\n")
        }

    }
}
