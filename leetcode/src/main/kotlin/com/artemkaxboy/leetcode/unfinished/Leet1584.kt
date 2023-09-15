package com.artemkaxboy.leetcode.unfinished

import com.artemkaxboy.leetcode.LeetUtils.toIntArray
import kotlin.system.measureTimeMillis

class Leet1584 {

    class Solution {
        fun minCostConnectPoints(points: Array<IntArray>): Int {
            val lastPoint = points.size - 1
            var result = 0

            val toDiscover = (0..lastPoint).toMutableSet()
            val distances = (0..lastPoint).asSequence()
                .map { kotlin.math.abs(points[0][0] - points[it][0]) + kotlin.math.abs(points[0][1] - points[it][1]) }
                .withIndex()
                .sortedBy { it.value }

            fun connectToNearest(i: Int, except: MutableSet<Int> = mutableSetOf(i)) {
                println("connect points[$i] ${points[i].joinToString("x")} to ...")
                val nearest = (0..lastPoint).asSequence()
                    .filter { it != i }
                    .filterNot { except.contains(it) }
                    .map { it to kotlin.math.abs(points[i][0] - points[it][0]) + kotlin.math.abs(points[i][1] - points[it][1]) }
                    .minBy { it.second }
                println("points[${nearest.first}] ${points[nearest.first].joinToString("x")}")
                result += nearest.second
                toDiscover.remove(nearest.first)
                connectToNearest(nearest.first, except.apply { add(nearest.first) })
            }

            for (v in distances) {
                if (toDiscover.remove(v.index)) {
                    connectToNearest(v.index)
                }
            }

            return result
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 =
                Data("[0,0],[2,2],[3,10],[5,2],[7,0]".split("],[").map { it.toIntArray() }.toTypedArray(), 20)
            val testCase2 = Data("[3,12],[-2,5],[-4,1]".split(",").map { it.toIntArray() }.toTypedArray(), 18)
            doWork(testCase1)
            doWork(testCase2)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = solution.minCostConnectPoints(data.input)
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
