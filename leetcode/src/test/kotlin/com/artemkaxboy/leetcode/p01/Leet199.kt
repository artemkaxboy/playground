package com.artemkaxboy.leetcode.p01

import com.artemkaxboy.leetcode.AbstractLeet
import com.artemkaxboy.leetcode.LeetUtils.toIntList
import com.artemkaxboy.leetcode.TreeNode
import com.artemkaxboy.leetcode.TreeNode.Companion.toTreeNode
import org.junit.jupiter.api.Test
import java.util.*

/**
 * NO IDE SOLUTION
 *
 * Runtime  147ms   Beats   100.0%
 * Memory   35.7MB  Beats   70.59%
 */
private class Leet199 : AbstractLeet() {

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
        fun rightSideView(root: TreeNode?): List<Int> {
            val discovered = ArrayList<Int>()

            discoverMostRight(discovered, root)

            return discovered
        }

        private fun discoverMostRight(discovered: ArrayList<Int>, node: TreeNode?, level: Int = 1) {
            if (node == null) return

            if (level > discovered.size) {
                discovered.add(node.`val`)
            }

            val newLevel = level + 1
            node.right?.let { discoverMostRight(discovered, it, newLevel) }
            node.left?.let { discoverMostRight(discovered, it, newLevel) }
        }
    }

    @Test
    fun check() {
        val testCases = LinkedList<Pair<Collection<*>, *>>()
        testCases.add(listOf("[1,2,3,null,5,null,4]".toTreeNode()) to "[1,3,4]".toIntList())
        testCases.add(listOf("[1,null,3]".toTreeNode()) to "[1,3]".toIntList())
        testCases.add(listOf("[]".toTreeNode()) to "[]".toIntList())

        val solution = Solution()
        doWork(testCases, solution::rightSideView)
    }
}
