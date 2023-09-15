package com.artemkaxboy.leetcode.p16

import com.artemkaxboy.leetcode.LeetUtils.toIntArray
import kotlin.system.measureTimeMillis

class Leet1631 {

    class Solution {
        fun minimumEffortPath(heights: Array<IntArray>): Int {

            val lastRow = heights.size - 1
            val lastCol = heights[0].size - 1
            val efforts = Array(lastRow + 1) { IntArray(lastCol + 1) { Int.MAX_VALUE } }

            fun findEasiest(x: Int, y: Int, currentEffort: Int) { // from l t r b
                if (x == lastCol && y == lastRow) {
                    efforts[y][x] = currentEffort
                    return
                }
                val currentHeight = heights[y][x]

                if (x < lastCol) {
                    val ef = maxOf(currentEffort, kotlin.math.abs(currentHeight - heights[y][x + 1]))
                    if (ef < efforts[y][x + 1]) {
                        efforts[y][x + 1] = ef
                        findEasiest(x + 1, y, ef)
                    }
                }
                if (x > 0) {
                    val ef = maxOf(currentEffort, kotlin.math.abs(currentHeight - heights[y][x - 1]))
                    if (ef < efforts[y][x - 1]) {
                        efforts[y][x - 1] = ef
                        findEasiest(x - 1, y, ef)
                    }
                }
                if (y < lastRow) {
                    val ef = maxOf(currentEffort, kotlin.math.abs(currentHeight - heights[y + 1][x]))
                    if (ef < efforts[y + 1][x]) {
                        efforts[y + 1][x] = ef
                        findEasiest(x, y + 1, ef)
                    }
                }
                if (y > 0) {
                    val ef = maxOf(currentEffort, kotlin.math.abs(currentHeight - heights[y - 1][x]))
                    if (ef < efforts[y - 1][x]) {
                        efforts[y - 1][x] = ef
                        findEasiest(x, y - 1, ef)
                    }
                }

            }
            findEasiest(0, 0, 0)

            return efforts[lastRow][lastCol]
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val cases = listOf(
                "[[1,2,2],[3,8,2],[5,3,5]]" to 2,
                "[[1,2,3],[3,8,4],[5,3,5]]" to 1,
                "[[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]" to 0,
                "[[3]]" to 0,
            )

            for (case in cases) {
                val testCase1 = Data(case.first.split("],[").map { it.toIntArray() }.toTypedArray(), case.second)
                doWork(testCase1)
            }
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = solution.minimumEffortPath(data.input)
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
        val input: Array<IntArray>,
        val expected: Int
    )
}
