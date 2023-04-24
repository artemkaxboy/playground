package com.artemkaxboy.leetcode

import org.assertj.core.api.Assertions
import java.util.*
import kotlin.reflect.KFunction
import kotlin.system.measureTimeMillis

abstract class AbstractLeet {

    companion object {
        fun <R> doWork(
            dataset: LinkedList<Pair<Collection<*>, R>>,
            solution: KFunction<*>
        ) {
            // warm up
            solution.call(*dataset.first.first.toTypedArray())

            dataset.forEach { (data, expectation) ->

                val result: R
                val time = measureTimeMillis {

                    @Suppress("UNCHECKED_CAST")
                    result = (solution.call(*data.toTypedArray()) as? R)!!
                }

                val out = if (result == expectation) System.out else System.err

                out.println("\nInput:  ${dataToString(data)}")
                out.println("Expect: $expectation")
                out.println("Result: $result")
                out.println("Time:   ${time}ms")

                Assertions.assertThat(result).isEqualTo(expectation)
            }
        }

        private fun dataToString(data: Iterable<*>): String {

            val sb = StringBuilder()

            data.forEach { item ->
                when (item) {
                    is Array<*> -> item.joinToString(prefix = "[", postfix = "]")
                    is IntArray -> item.joinToString(prefix = "[", postfix = "]")
                    is DoubleArray -> item.joinToString(prefix = "[", postfix = "]")
                    is Iterable<*> -> "[" + dataToString(item) + "]"
                    else -> item.toString()
                }.also { sb.append(it) }
                sb.append(", ")
            }

            return sb.toString()
        }
    }
}
