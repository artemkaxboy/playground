package com.artemkaxboy.leetcode.p00

/**
 * Optimizations
 * Runtime      140ms   Beats 97.72%
 * Memory       35.5MB  Beats 78.27%
 */
class Leet14 {
    class Solution {
        fun longestCommonPrefix(strs: Array<String>): String {

            val lastElement = strs.size - 1

            val commonPrefixLength = intArrayOf(strs[0].length)
            var i = 1
            while (i <= lastElement) {
                val l = strs[i].length
                if (l < commonPrefixLength[0])
                    commonPrefixLength[0] = l
                if (l == 0) break
                i++
            }

            i = 1
            while (i <= lastElement) {
                var j = 0
                while (j < commonPrefixLength[0]) {
                    if (strs[0][j] != strs[i][j]) {
                        commonPrefixLength[0] = j
                        break
                    }
                    j++
                }
                i++
            }
            return strs[0].subSequence(0, commonPrefixLength[0]).toString()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = arrayOf("floa", "floa", "flolll")
            doWork(testCase1)
        }

        private fun doWork(data: Array<String>) {
            val solution = Solution()

            val result = solution.longestCommonPrefix(data)

            println("Data: $data")
            println("Result: $result\n")
        }
    }
}
