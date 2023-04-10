package com.artemkaxboy.leetcode.p01

/**
 * Optimizations
 * Runtime      158ms   Beats 86.92%
 * Memory       35.4MB  Beats 75.39%
 */
class Leet118 {

    class Solution {

        fun generate(numRows: Int): List<List<Int>> {
            val result = ArrayList<List<Int>>(numRows)
            var rowIndex = 0
            while (rowIndex < numRows) {
//                println("rowIndex: $rowIndex")

                val row = ArrayList<Int>(rowIndex + 1)
                var elementIndex = 0
                while (elementIndex <= rowIndex) {
                    val rowNumber = rowIndex + 1
                    if (elementIndex == 0 || elementIndex == rowIndex) {
//                        println("$elementIndex: const")
                        row.add(1)
                    } else if (elementIndex >= rowNumber - (rowNumber / 2)) {
//                        println("$elementIndex: copy")
                        row.add(row[rowIndex - elementIndex])
                    } else {
//                        println("$elementIndex: calc")
                        row.add(result[rowIndex - 1][elementIndex - 1] + result[rowIndex - 1][elementIndex])
                    }
                    elementIndex++
                }
                result.add(row)
                rowIndex++
            }
            return result
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = 1 to "[[1]]"
            doWork(testCase1)

            val testCase2 = 15 to "[[1]," +
                    " [1, 1]," +
                    " [1, 2, 1]," +
                    " [1, 3, 3, 1]," +
                    " [1, 4, 6, 4, 1]," +
                    " [1, 5, 10, 10, 5, 1]," +
                    " [1, 6, 15, 20, 15, 6, 1]," +
                    " [1, 7, 21, 35, 35, 21, 7, 1]," +
                    " [1, 8, 28, 56, 70, 56, 28, 8, 1]," +
                    " [1, 9, 36, 84, 126, 126, 84, 36, 9, 1]," +
                    " [1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1]," +
                    " [1, 11, 55, 165, 330, 462, 462, 330, 165, 55, 11, 1]," +
                    " [1, 12, 66, 220, 495, 792, 924, 792, 495, 220, 66, 12, 1]," +
                    " [1, 13, 78, 286, 715, 1287, 1716, 1716, 1287, 715, 286, 78, 13, 1]," +
                    " [1, 14, 91, 364, 1001, 2002, 3003, 3432, 3003, 2002, 1001, 364, 91, 14, 1]]"
            doWork(testCase2)
        }

        private fun doWork(data: Pair<Int, Any>) {
            val solution = Solution()

            val result = solution.generate(data.first)

            println("Data:     ${data.first}")
            println("Expected: ${data.second}")
            println("Result:   $result\n")
        }

    }
}
