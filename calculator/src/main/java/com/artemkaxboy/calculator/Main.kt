package com.artemkaxboy.calculator

fun main(args: Array<String>) {
    val pattern = Regex("(.+)([\\-+/*])(.+)")
    while (true) {
        val toCalc = readln()
        pattern.find(toCalc)?.let {


            val v1 = Num.fromInput(it.groups[1]!!.value)
            val v2 = Num.fromInput(it.groups[3]!!.value)
            val operator = it.groups[2]!!.value

            val result = when (operator) {
                "+" -> (v1 + v2).toString()
                "-" -> (v1 - v2).toString()
                "*" -> (v1 * v2).toString()
                "/" -> (v1 / v2).toString()
                else -> "unknown operation"
            }
            println("result: $result")
        } ?: println("incorrect input")
    }
}
