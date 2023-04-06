package com.artemkaxboy.leetcode.p11

/**
 * Runtime:     1809ms      Beats:  31.30%
 * Memory:      94.4MB      Beats:  44.83%
 */
class Leet1146 {

    class SnapshotArray(length: Int) {

        var snapCounter = 0
        val actions = ArrayList<Pair<Int, Int>>()
        val snaps = HashMap<Int, Int>()

        fun set(index: Int, `val`: Int) {
            actions.add(Pair(index, `val`))
        }

        fun snap(): Int {
            val snapId = snapCounter++
            snaps[snapId] = actions.size
            actions.add(Pair(-1, -1))
            return snapId
        }

        fun get(index: Int, snap_id: Int): Int {
            val actionIndex = snaps[snap_id]!!
            (actionIndex downTo 0).forEach {
                if (actions[it].first == index) {
                    return actions[it].second
                }
            }
            return 0
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = ""
            doWork(testCase1)
        }

        private fun doWork(data: Any) {
            val solution = SnapshotArray(3)

            solution.set(0, 5)
            solution.snap()
            solution.set(0, 6)
            val result = solution.get(0,0)

            println("Data: $data")
            println("Result: $result\n")
        }

    }
}
