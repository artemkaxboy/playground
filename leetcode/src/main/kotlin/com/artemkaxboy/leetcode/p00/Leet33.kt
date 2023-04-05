package com.artemkaxboy.leetcode.p00

/*
NO IDE SOLUTION

            Value       Beats
Runtime:    159 ms      83.72%
Memory:     35.3 MB     75.29%

Test cases:

[4,5,6,7,0,1,2] 0 = 4
[4,5,6,7,0,1,2] 3 = -1
[1] 0 = -1
[1,3] 2 = -1
[1,2,3,4,5,6] 4 = 3
[4,5,6,7,8,1,2,3] 8 = 4
[5,1,2,3,4] 1 = 1
[9,0,2,7,8] 3 = -1
 */
private class Leet33 {
    fun search(nums: IntArray, target: Int): Int {
        // println("nums: ${nums.joinToString()}")
        // println("target: $target")

        var start = 0
        var end = maxOf(0, nums.size - 1)
        while(start != end) {
            val startNum = nums[start]
            val endNum = nums[end]

            if (target == startNum) return start
            if (target == endNum) return end

            val middle = start + (end - start) / 2
            if (middle == start) return -1
            val middleNum = nums[middle]

            if (target == middleNum) return middle

            val headInLeft = startNum > middleNum
            val headInRight = middleNum > endNum
            val headIsHead = !headInLeft && !headInRight

            if (target > middleNum) {
                if (target > startNum) {
                    if (!headInLeft) {
                        start = middle + 1
                    } else {
                        end = maxOf(0, middle - 1)
                    }
                } else {
                    start = middle + 1
                }
            } else {
                if (target < endNum) {
                    if (!headInRight) {
                        end = maxOf(0, middle - 1)
                    } else {
                        start = middle + 1
                    }
                } else {
                    end = maxOf(0, middle - 1)
                }
            }
        }

        return if (nums[start] == target) start else -1
    }
}
