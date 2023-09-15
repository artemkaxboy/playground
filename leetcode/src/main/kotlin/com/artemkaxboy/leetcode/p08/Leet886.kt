package com.artemkaxboy.leetcode.p08

import com.artemkaxboy.leetcode.LeetUtils.toIntArray
import java.util.*
import kotlin.system.measureTimeMillis

class Leet886 {

    class Solution {

        fun possibleBipartition(n: Int, dislikes: Array<IntArray>): Boolean {

            val dislikeMap = HashMap<Int, LinkedList<Int>>(n, 1.0f)

            for (dislike in dislikes) {
                dislikeMap.compute(dislike[0]) { k, v -> (v ?: LinkedList()).also { it.add(dislike[1]) } }
                dislikeMap.compute(dislike[1]) { k, v -> (v ?: LinkedList()).also { it.add(dislike[0]) } }
            }

            var next = dislikeMap.entries.firstOrNull()
            while (next != null) {
                val discovered = HashSet<Int>().apply { add(next!!.key) }
                val queue = LinkedList<Pair<Boolean, Int>>().apply { add(true to next!!.key) }
                val clusters = mapOf(true to HashSet<Int>().apply { add(next!!.key) }, false to HashSet())

                while (queue.isNotEmpty()) {
                    val (cluster, toDiscover) = queue.poll()
                    val opposites = dislikeMap.remove(toDiscover)!!

                    val selfCluster = clusters[cluster]!!
                    val oppositeCluster = clusters[!cluster]!!
                    for (opposite in opposites) {
                        if (selfCluster.contains(opposite)) return false
                        oppositeCluster.add(opposite)
                        if (discovered.add(opposite)) {
                            queue.add(!cluster to opposite)
                        }
                    }
                }

                next = dislikeMap.entries.firstOrNull()
            }

            return true
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("", "")
            doWork(testCase1)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result: Any
            val time = measureTimeMillis {
                result = solution.possibleBipartition(
                    300,
                    "[[1,2],[2,3],[1,3]".split("],[").map { it.toIntArray() }.toTypedArray()
                )
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
        val input: Any,
        val expected: Any
    )
}
