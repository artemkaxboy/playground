package com.artemkaxboy.leetcode

import java.util.*

data class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null

    companion object {
        fun fromString(nodes: String): TreeNode? {
            val list = nodes.trim('[', ']').split(",")
                .map { it.toIntOrNull() }
            val root = list.firstOrNull()?.let { TreeNode(it) } ?: return null
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
