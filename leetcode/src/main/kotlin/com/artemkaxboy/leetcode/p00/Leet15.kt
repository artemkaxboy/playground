package com.artemkaxboy.leetcode.p00

import com.artemkaxboy.leetcode.LeetUtils.toIntArray
import java.util.LinkedList
import java.util.TreeMap
import kotlin.system.measureTimeMillis

class Leet15 {

    class Solution {
        fun threeSum(input: IntArray): List<List<Int>> {

            val result = LinkedList<List<Int>>()
            val map = TreeMap<Int, Int>()
            for (i in input) {
                map[i] = map[i]?.plus(1) ?: 1
            }

            for (l1 in map.headMap(0)) {

                if (l1.value > 1) {
                    val candidate = -(l1.key + l1.key)
                    if (map.containsKey(candidate)) {
                        result.add(listOf(l1.key, l1.key, candidate))
                    }
                }
                for (l2 in map.tailMap(l1.key + 1)) {

                    if (l2.value > 1 && l1.key + l2.key * 2 == 0) {
                        result.add(listOf(l1.key, l2.key, l2.key))
                    }

                    val candidate = -(l1.key + l2.key)
                    if (map.tailMap(l2.key + 1).containsKey(candidate)) {
                        result.add(listOf(l1.key, l2.key, candidate))
                    }
                }
            }
            if ((map[0]?.compareTo(3) ?: -1) >= 0) result.add(listOf(0, 0, 0))

            return result
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase0 = Data("[-1,0,0,0,1,2,-1,-4]".toIntArray(), "[[-1,-1,2],[-1,0,1],[0,0,0]]".split("],[").map { it.toIntArray().toList() }.toList())
            val testCase1 = Data("[-1,0,1,2,-1,-4]".toIntArray(), "[[-1,-1,2],[-1,0,1]]".split("],[").map { it.toIntArray().toList() }.toList())
            val testCase2 = Data("[0,1,1]".toIntArray(), listOf())
            val testCase3 = Data("[0,0,0]".toIntArray(), "[[0,0,0]]".split("],[").map { it.toIntArray().toList() }.toList())
            doWork(testCase0)
            doWork(testCase1)
            doWork(testCase2)
            doWork(testCase3)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = solution.threeSum(data.input)
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
        val input: IntArray,
        val expected: List<List<Int>>
    )
}
