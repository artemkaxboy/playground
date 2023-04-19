package com.artemkaxboy.leetcode.p00

import com.artemkaxboy.leetcode.LeetUtils

/**
 * Runtime:     217ms       Beats:   50.88%
 * Memory:      37.5MB      Beats:   54.42%
 */
class Leet46 {

    class Solution {
        fun permute(nums: IntArray): List<List<Int>> {
            var i = 1
            val size = nums.size
            var result: List<List<Int>> = emptyList()
            while (i <= size) {
                result = variants(nums[i - 1], result, i)
                i++
            }
            return result
        }

        fun variants(newNum: Int, existing: List<List<Int>>, iteration: Int): List<List<Int>> {

            val existingSize = existing.size
            val newSize = if (existingSize > 0) existing.size * iteration else 1
            val newList = ArrayList<List<Int>>(newSize)

            if (existingSize == 0) {
                newList.add(listOf(newNum))
            } else {
                var i = 0
                while (i < iteration) {
                    var j = 0
                    while (j < existingSize) {
                        newList.add(existing[j].take(i) + newNum + existing[j].drop(i))
                        j++
                    }
                    i++
                }
            }

            return newList
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data(
                "[1,2,3]", listOf(
                    listOf(1, 2, 3),
                    listOf(2, 1, 3),
                    listOf(1, 3, 2),
                    listOf(2, 3, 1),
                    listOf(3, 1, 2),
                    listOf(3, 2, 1)
                )
            )
            doWork(testCase1)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result = solution.permute(LeetUtils.stringToIntArray(data.input))

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result\n")
        }
    }

    data class Data(
        val input: String,
        val expected: List<List<Int>>
    )
}
