package com.artemkaxboy.leetcode.p02

import java.util.*

class Leet230 {

    class Solution {
        fun kthSmallest(root: TreeNode?, k: Int): Int {
            val list = LinkedList<TreeNode>()
            bTreeToLinkedList(root, list)
            return list.getOrNull(k - 1)?.`val` ?: 0
        }

        private fun bTreeToLinkedList(
            node: TreeNode?,
            list: LinkedList<TreeNode>,
        ) {
            if (node != null) {


                result.addAll(bTreeToLinkedList(node.left))
                result.add(node)
                result.addAll(bTreeToLinkedList(node.right))
            }
            return result
        }
    }

    data class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("[3,1,4,null,2]", 1, 1)
            doWork(testCase1)

            val testCase2 = Data("[5,3,6,2,4,null,null,1]", 3, 3)
            doWork(testCase2)

//            val testCase3 = Data("[3,1,4,null,2]", 1, 1)
//            doWork(testCase3)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result = solution.kthSmallest(data.getTreeNode(), data.k)

            println("Data:     $data")
            println("Result:   $result\n")
        }
    }

    data class Data(
        val nodes: String,
        val k: Int,
        val expected: Int,
    ) {
        fun getTreeNode(): TreeNode {
            val list = nodes.trim('[', ']').split(",")
                .map { it.toIntOrNull() }
            val root = TreeNode(list.first()!!)
            val queueToFill: Queue<TreeNode?> = LinkedList<TreeNode?>().apply { offer(root) }

            var index = 1
            while (index < list.size) {
                val toFill = queueToFill.poll()
                toFill?.left = list.getOrNull(index)?.let { TreeNode(it) }.also { queueToFill.offer(it) }
                toFill?.right = list.getOrNull(index + 1)?.let { TreeNode(it) }.also { queueToFill.offer(it) }
                index += 2
            }
            return root
        }
    }
}
