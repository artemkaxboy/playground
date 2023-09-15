package com.artemkaxboy.leetcode.p00

import java.util.*

/**
 * NO IDE SOLUTION
 *
 * Runtime  141ms   Beats   85.76%
 * Memory   33.9MB  Beats   82.27%
 */
class Leet94 {

    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     * class TreeNode(var `val`: Int) {
     *     var left: TreeNode? = null
     *     var right: TreeNode? = null
     * }
     */
    class Solution {

        val resultList = LinkedList<Int>()

        fun inorderTraversal(root: TreeNode?): List<Int> {
            root?.also { addWithChildren(resultList, it) }
            // root?.also { resultList.add(it.`val`) }
            // root?.left?.also { addWithChildren(resultList, it) }
            // root?.right?.also { addWithChildren(resultList, it) }
            return resultList
        }

        fun addWithChildren(result: LinkedList<Int>, node: TreeNode?) {
            node?.left?.also { addWithChildren(result, it) }
            node?.also { result.add(it.`val`) }
            node?.right?.also { addWithChildren(result, it) }
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "" to ""
            doWork(testCase1)
        }

        private fun doWork(data: Pair<Any, Any>) {
            val solution = Solution()

            val result = "comment me"
//            val result = solution.process(data)

            println("Data:     ${data.first}")
            println("Expected: ${data.second}")
            println("Result:   $result\n")
        }

    }
}
