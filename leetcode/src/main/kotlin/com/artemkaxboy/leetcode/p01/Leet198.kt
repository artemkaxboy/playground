package com.artemkaxboy.leetcode.p01

import com.artemkaxboy.leetcode.LeetUtils.toIntArray
import kotlin.system.measureNanoTime

class Leet198 {

    class MySolution {
        private val knownResults = HashMap<Int, Pair<Int, Int>>()
        // nanotime = 82218, 36926, 47676, 2171884, 21085261

        fun rob(nums: IntArray): Int {

            return maxOf(robNext(nums), robNext(nums, 0, 1))
        }

        private fun robNext(nums: IntArray, sum: Int = 0, allowedIndex: Int = 0): Int {
            if (allowedIndex >= nums.size) return sum

            knownResults[allowedIndex]?.takeIf { it.first >= sum }?.let { return it.second }

            if (allowedIndex == nums.size - 1) {
                return sum + nums[allowedIndex].also { knownResults[allowedIndex] = sum to it }
            }

            return maxOf(
                robNext(nums, sum + nums[allowedIndex], allowedIndex + 2),
                robNext(nums, sum + nums[allowedIndex + 1], allowedIndex + 3)
            ).also { knownResults[allowedIndex] = sum to it }
        }
    }

    /**
     * This is a method named rob that takes an array of integers as input, and returns a single integer.
     * The purpose of this method is to solve the "House Robber" problem: given an array of non-negative integers
     * representing the amount of money of each house, determine the maximum amount of money you can rob tonight
     * without alerting the police.
     *
     * The implementation uses dynamic programming, specifically memoization, to solve the problem efficiently.
     * It starts with two variables, prev1 and prev2, both initially set to 0. Then, for each element in the input
     * array, it calculates the maximum amount of money that can be robbed up to that point by choosing between
     * robbing the current house plus the previous previous house (prev2 + nums[i]), or skipping the current house
     * and using the maximum value seen so far (prev1).
     *
     * To avoid counting duplicates, the previous maximum value is stored in a temporary variable temp, which is
     * used to update prev2 after prev1 has been updated. The final result is stored in prev1 and returned at the
     * end of the function.
     *
     * Overall, this algorithm achieves a time complexity of O(n), where n is the length of the input array,
     * and a space complexity of O(1) because the only memory used are the two variables prev1 and prev2.
     *
     *
     * Данный код на Kotlin решает задачу "Грабитель домов". В этой задаче имеется массив неотрицательных чисел,
     * каждый элемент которого представляет собой количество денег в доме. Требуется определить максимальную сумму
     * денег, которую можно украсть без привлечения полиции.
     *
     * Алгоритм использует метод динамического программирования, а именно мемоизацию, для эффективного решения задачи.
     * Он начинается со значения двух переменных prev1 и prev2, равных 0. Затем, для каждого элемента входного массива,
     * вычисляется максимальная возможная сумма денег, которую можно украсть к данному моменту времени, выбрав между
     * грабежом текущего дома плюс дома, предшествующего предыдущему (prev2 + nums[i]) или пропустив текущий дом и
     * используя максимальное значение, найденное до этого (prev1).
     *
     * Для предотвращения повторов предыдущее максимальное значение хранится во временной переменной temp, которая
     * используется для обновления значения prev2 после того, как значение prev1 было обновлено. Конечный результат
     * хранится в prev1 и возвращается в конце функции.
     *
     * В целом, данный алгоритм достигает временной сложности O(n), где n - длина входного массива, и пространственной
     * сложности O(1), потому что единственные переменные, используемые в процессе выполнения, это prev1 и prev2.
     *
     *
     */
    class BestSolution {

        // nanoTime = 55032, 33021, 35335, 705249, 1354351
        fun rob(nums: IntArray): Int {
            var prev1 = 0
            var prev2 = 0

            for (i in nums.indices) {
                val temp = prev1
                prev1 = Math.max(prev1, prev2 + nums[i])
                prev2 = temp
            }

            return prev1
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val testCase1 = Data("[1,90,100,90,1]", 180)
            val testCase2 = Data("[100,90,100,90,100]", 300)
            val testCase3 = Data("[100,5,5,100,5,5,100]", 300)
            val testCase4 =
                Data(
                    "[226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166,124]",
                    7102,
                )
            val testCase5 =
                Data(
                    "[" +
                            "226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166," +
                            "226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166," +
                            "226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166," +
                            "226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166," +
                            "226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,82,35,120,180,249,106,37,169,225,54,103,55,166," +
                            "124]",
                    34846,
                )
            doWork(testCase1)
            doWork(testCase1)
            doWork(testCase2)
            doWork(testCase3)
            doWork(testCase4)
            doWork(testCase5)
        }

        private fun doWork(data: Data) {
//            val solution = MySolution()
            val solution = BestSolution()

            val result: Any
            val time = measureNanoTime {
                result = solution.rob(data.input.toIntArray())
            }

            println("Data:     ${data.input}")
            println("Expected: ${data.expected}")
            println("Result:   $result")
            println("Time:     $time\n")

            if (data.expected != result) {
                throw AssertionError("\nExpected: ${data.expected}\nActual:   $result")
            }
        }
    }

    data class Data(
        val input: String,
        val expected: Int
    )
}
