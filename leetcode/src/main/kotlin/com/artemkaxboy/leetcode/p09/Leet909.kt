package com.artemkaxboy.leetcode.p09

import com.artemkaxboy.leetcode.LeetUtils
import java.util.LinkedList

/**
 * Accepted
 *
 * Runtime:     335ms       Beat:   33.33%
 * Memory:      39.5MB      Beat:   33.33%
 */
class Leet909 {

    private lateinit var board: Array<IntArray>
    private val explored = HashSet<Square>()
    private val toExplore = LinkedList<Square>()
    private var size = 0
    private var lastRow = 0
    private var lastCol = 0
    private var lastBusinessSquare = 0

    fun snakesAndLadders(board: Array<IntArray>): Int {
        this.board = board
        size = board.size
        lastBusinessSquare = size * size
        lastRow = size - 1
        lastCol = lastRow

        val start = Square(0, lastRow, 0, this::numberOf)
        toExplore.push(start)

        return findShortestWay()
    }

    private fun findShortestWay(): Int {
        while (toExplore.isNotEmpty()) {
            val current = toExplore.pop()
            if (explored.add(current)) {
                if (current.number == lastBusinessSquare)
                    return current.moves
//                println("added: $current")
                explore(current).forEach { toExplore.add(it) }
            }
        }
        return -1
    }

    private fun numberOf(square: Square): Int {
        val rowBusinessNumber = size - 1 - square.y
        val colBusinessNumber = if (rowBusinessNumber % 2 == 0) {
            square.x
        } else {
            size - 1 - square.x
        }
        return 1 + colBusinessNumber + rowBusinessNumber * size
    }

    private fun businessMove(square: Square, move: Int, count: Boolean = true): Square {
        return minOf(square.number + move, lastBusinessSquare)
            .let { number ->
                val preLast = number - 1
                val y = lastCol - preLast / size
                val x = if ((lastCol - y) % 2 == 0) preLast % size else lastCol - (preLast % size)
                square.copy(x = x, y = y, moves = if (count) square.moves + 1 else square.moves, parent = square)
            }
    }

    private fun explore(square: Square): Collection<Square> {
        return (1..6).mapNotNull { businessMove(square, it) }
            .map {
                val hack = board[it.y][it.x]
                if (hack == -1) it else businessMove(it, hack - it.number, false)
            }
            .filter { !explored.contains(it) }
    }

    data class Square(val x: Int, val y: Int, val moves: Int, val numberCalc: (Square) -> Int, val parent: Square? = null) {

        var number = 0
            get(): Int {
                if (field == 0) {
                    field = numberCalc(this)
                }
                return field
            }


        override fun equals(other: Any?): Boolean {
            other as Square

            if (x != other.x) return false
            return y == other.y
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }

        override fun toString(): String {
            return "Square(x=$x, y=$y, moves=$moves, number=$number)"
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 =
                "[[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]" to 4
            doWork(testCase1)

            val testCase2 = "[[-1,-1],[-1,3]]" to 1
            doWork(testCase2)
        }

        private fun doWork(data: Pair<String, Int>) {
            val solution = Leet909()

            val prepared = data.first.split("],[").map { LeetUtils.stringToIntArray(it) }.toTypedArray()
            val result = solution.snakesAndLadders(prepared)

            println("Data: ${data.first}")
            println("Expected: ${data.second}")
            println("  Result: $result\n")
        }

    }
}
