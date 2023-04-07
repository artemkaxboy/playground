package com.artemkaxboy.leetcode.p00

import com.artemkaxboy.leetcode.LeetUtils
import java.util.Collections
import java.util.TreeMap

/**
 * Hard
 * Runtime  653ms   Beats   10.84%
 * Memory   45.6MB  Beats   15.27%
 */
class Leet42 {

    fun trap(height: IntArray): Int {
        val firstIndex = 0
        val lastIndex = height.size - 1

        val peakPairs = TreeMap<Int, IntArray>(Collections.reverseOrder())

        height.withIndex()
            .filter { it.value >= maxOf(it.index - 1, firstIndex).let { p -> height[p] } }
            .filter { it.value >= minOf(it.index + 1, lastIndex).let { n -> height[n] } }
            .map { (index, value) ->
                peakPairs.computeIfAbsent(value) { intArrayOf(index, index) }[1] = index
            }

        var min = Int.MAX_VALUE
        var max = 0
        var water = 0
        for (i in peakPairs.keys) {
            val pair = peakPairs[i]!!

            min = minOf(min, pair[0])
            max = maxOf(max, pair[1])

            val heightOfCurrentKey = i - (peakPairs.ceilingKey(i - 1) ?: 0)
            var rowWater = 0
            for (checkWaterIndex in min + 1 until max) {
                rowWater += minOf(maxOf(i - height[checkWaterIndex], 0), heightOfCurrentKey)
            }

//            println("key: $i, next: ${peakPairs.ceilingKey(i - 1)}, height: $heightOfCurrentKey, rowWater: $rowWater")
            water += rowWater
        }

//        println(peakPairs.map { "${it.key} = ${it.value.joinToString()}" })
        return water
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "[0,1,0,2,1,0,1,3,2,1,2,1]" to 6
            doWork(testCase1)

            val testCase2 = "[4,2,0,3,2,5]" to 9
            doWork(testCase2)

            val myCase = "[2,0,3,0,4,2,0,3,2,5,4,5]" to 15
            doWork(myCase)

            val myCase2 = "[100,50,100,150,100,50,100]" to 100
            doWork(myCase2)
        }

        private fun doWork(data: Pair<String, Int>) {
            val solution = Leet42()

            val result = solution.trap(LeetUtils.stringToIntArray(data.first))

            println("    Data: $data")
            println("  Result: $result")
            println("Expected: ${data.second}\n")
        }

    }
}
