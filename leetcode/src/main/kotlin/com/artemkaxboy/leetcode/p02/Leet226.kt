package com.artemkaxboy.leetcode.p02

/**
 * NO IDE SOLUTION
 *
 * Runtime:     133ms       Beats:   92.83%
 * Memory:      33.2MB      Beats:   98.45%
 */
class Leet226 {

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
        fun invertTree(root: TreeNode?): TreeNode? {
            return invertNode(root)
        }

        private fun invertNode(node: TreeNode?): TreeNode? {
            if (node == null) return null
            return TreeNode(node.`val`).apply {
                left = invertNode(node.right)
                right = invertNode(node.left)
            }
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "[4,2,7,1,3,6,9]" to "[4,7,2,9,6,3,1]"
            doWork(testCase1)

            val testCase2 = "[2,1,3]" to "[2,3,1]"
            doWork(testCase2)

            val testCase3 = "[]" to "[]"
            doWork(testCase3)
        }

        private fun doWork(data: Pair<String, String>) {
            val solution = Solution()

//            val result = solution.invertTree(data.first)

            println("Data:     ${data.first}")
            println("Expected: ${data.second}")
//            println("Result:   $result\n")
        }

    }
}
