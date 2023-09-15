package com.artemkaxboy.leetcode.p02

import com.artemkaxboy.leetcode.TreeNode
import java.util.*

/**
 * Runtime      199ms   Beats 83.59%
 * Memory       37.2MB  Beats 89.60%
 */
class Leet236 {

    class Solution {
        fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {

            val (pNode, qNode) = bfs(root, p, q)
            var levelToCheck = minOf(pNode!!.level, qNode!!.level)

            var pPeek = pNode
            while (pPeek!!.level > levelToCheck) pPeek = pPeek.parent

            var qPeek = qNode
            while (qPeek!!.level > levelToCheck) qPeek = qPeek.parent

            while (pPeek != null) {
                if (qPeek?.node?.`val` == pPeek.node?.`val`) return qPeek?.node
                pPeek = pPeek.parent
                qPeek = qPeek!!.parent
            }
            return null
        }

        private fun bfs(root: TreeNode?, p: TreeNode?, q: TreeNode?): Pair<Node?, Node?> {
            val toCheck: Queue<Node> = LinkedList<Node>().apply { offer(Node(root, null, 0)) }
            var pNode: Node? = null
            var qNode: Node? = null
            while (toCheck.isNotEmpty()) {
                val currentNode = toCheck.poll()

                if (currentNode.node?.`val` == p!!.`val`) {
                    pNode = currentNode
                    if (qNode != null) break
                }
                if (currentNode.node?.`val` == q!!.`val`) {
                    qNode = currentNode
                    if (pNode != null) break
                }

                val nextLevel = currentNode.level + 1

                currentNode.node?.left?.let { Node(it, currentNode, nextLevel) }?.also { toCheck.offer(it) }
                currentNode.node?.right?.let { Node(it, currentNode, nextLevel) }?.also { toCheck.offer(it) }
            }
            return pNode to qNode
        }

        data class Node(val node: TreeNode?, val parent: Node?, val level: Int)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("[3,5,1,6,2,0,8,null,null,7,4]", 5, 1, 3)
            doWork(testCase1)
        }

        private fun doWork(data: Data) {
            val solution = Solution()

            val result = solution.lowestCommonAncestor(
                TreeNode.fromString(data.input), TreeNode(data.v1), TreeNode(data.v2)
            )

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result\n")
        }
    }

    data class Data(
        val input: String,
        val v1: Int,
        val v2: Int,
        val expected: Int,
    )
}
