package com.artemkaxboy.leetcode.p10

import com.artemkaxboy.leetcode.LeetUtils
import java.util.*

/**
 * Medium
 * Runtime  256ms   Beats   100.0%
 * Memory   37.7MB  Beats   50.00%
 */
class Leet1034 {

    class Solution {

        private lateinit var grid: Array<IntArray>
        private val queue: Queue<Pair<Int, Int>> = LinkedList()
        private lateinit var explored: HashSet<Pair<Int, Int>>
        private var wantedColor = 0
        private val border = LinkedList<Pair<Int, Int>>()

        fun colorBorder(grid: Array<IntArray>, row: Int, col: Int, color: Int): Array<IntArray> {
            val rows = grid.size
            val cols = grid[0].size
            val lastRow = rows - 1
            val lastCol = cols - 1

            wantedColor = grid[row][col]
            val item = row to col
            explored = HashSet(rows * cols)
            explored.add(item)
            queue.offer(item)
            this.grid = grid

            while (queue.isNotEmpty()) {
                val (iRow, iCol) = queue.poll()

                if (iRow > 0) addToQueueIfNeeded(iRow - 1, iCol)
                if (iRow < lastRow) addToQueueIfNeeded(iRow + 1, iCol)
                if (iCol > 0) addToQueueIfNeeded(iRow, iCol - 1)
                if (iCol < lastCol) addToQueueIfNeeded(iRow, iCol + 1)

                if (iRow == 0 || iRow == lastRow || iCol == 0 || iCol == lastCol) border.add(iRow to iCol)
                else if (
                    grid[iRow - 1][iCol] != wantedColor || grid[iRow + 1][iCol] != wantedColor
                    || grid[iRow][iCol - 1] != wantedColor || grid[iRow][iCol + 1] != wantedColor
                )
                    border.add(iRow to iCol)
            }

            while (border.isNotEmpty()) {
                val (r, c) = border.poll()
                grid[r][c] = color
            }
            return grid
        }

        private fun addToQueueIfNeeded(row: Int, col: Int) {
            val item = row to col
            if (!explored.contains(item)) {
                explored.add(item)
                if (grid[row][col] == wantedColor) {
                    queue.offer(item)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data(
                "[[1,1],[1,2]]".split("],[").map { LeetUtils.stringToIntArray(it) }.toTypedArray(),
                0, 0, 3,
                "[[3,3],[3,2]]",
            )
//            doWork(testCase1)

            val testCase2 = Data(
                "[[1,1,1],[1,1,1],[1,1,1]]".split("],[").map { LeetUtils.stringToIntArray(it) }.toTypedArray(),
                1, 1, 2,
                "[[2,2,2],[2,1,2],[2,2,2]]",
            )
//            doWork(testCase2)

            val testCase3 = Data(
                "[[1,1,1],[0,1,1],[1,1,1]]".split("],[").map { LeetUtils.stringToIntArray(it) }.toTypedArray(),
                1, 1, 2,
                "[[2,2,2],[0,2,2],[2,2,2]]",
            )
//            doWork(testCase3)

            val testCase4 = Data(
                "[[1,2,1,2,1,2],[2,2,2,2,1,2],[1,2,2,2,1,2]]".split("],[").map { LeetUtils.stringToIntArray(it) }
                    .toTypedArray(),
                1, 3, 1,
                "[[1,1,1,1,1,2],[1,2,1,1,1,2],[1,1,1,1,1,2]]",
            )
            doWork(testCase4)

        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result = solution.colorBorder(data.grid, data.row, data.col, data.color)
            val stringResult = result.joinToString(",", "[", "]") { it.joinToString(",", "[", "]") }

            println("Data:     $data")
            println("Result:   $stringResult\n")

//            assert(stringResult == data.expected)
            if (stringResult != data.expected) throw RuntimeException("Unexpected result\nwanted: ${data.expected}\n   got: $stringResult")
        }
    }

    data class Data(
        val grid: Array<IntArray>,
        val row: Int,
        val col: Int,
        val color: Int,
        val expected: String,
    )
}
