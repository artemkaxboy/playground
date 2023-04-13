package com.artemkaxboy.leetcode.p01

import java.util.*

/**
 * Runtime:     216ms       Beats:   27.61%
 * Memory:      40.7MB      Beats:   15.34%
 */
class Leet160 {

    class Solution {

        fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
            val list1 = LinkedList<ListNode>()
            var headA2 = headA
            while (headA2 != null) {
                list1.push(headA2)
                headA2 = headA2.next
            }

            val list2 = LinkedList<ListNode>()
            var headB2 = headB
            while (headB2 != null) {
                list2.push(headB2)
                headB2 = headB2.next
            }

            println(list1)
            println(list2)

            if (list1.isEmpty() || list2.isEmpty()) return null
            var index = 0
            while (index < list1.size && list1.getOrNull(index) == list2.getOrNull(index)) {
                index++
            }

            if (index == 0) return null

            return list1.get(index - 1)
        }
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null

        override fun toString(): String {
            return `val`.toString()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = "" to ""
            doWork(testCase1)
        }

        private fun doWork(data: Pair<Any, Any>) {
            val solution = Solution()

            val l10 = ListNode(10)
            val l9 = ListNode(9).apply { next = l10 }
            val l8 = ListNode(8).apply { next = l9 }
            val l71 = ListNode(71).apply { next = l8 }
            val l61 = ListNode(61).apply { next = l71 }
            val l72 = ListNode(72).apply { next = l8 }
            val result = solution.getIntersectionNode(l61, l72)

            println("Data:     ...")
            println("Expected: ..")
            println("Result:   $result\n")

            val m1 = ListNode(1)
            val resultM = solution.getIntersectionNode(m1, m1)

            println("Data:     ...")
            println("Expected: ..")
            println("Result:   $resultM\n")
        }

    }
}
