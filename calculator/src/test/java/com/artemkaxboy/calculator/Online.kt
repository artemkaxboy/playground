package com.artemkaxboy.calculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

/*
Есть словарь со списком зависимостей пакетов в формате

deps = {
"A" : ["D","B"],
"B" : ["C", "E"],
"C" : ["D", "E"]
}
Напишите функцию get_deps(), которая для заданного пакета вернет список всех его зависимостей (например, для A: "E", "D", "B", "C")
*/

class Solution {

    val explored = mutableSetOf<String>()

    fun main(deps: Map<String, Set<String>>, target: String): List<String> {

        val queue = LinkedList<String>()
        queue.offer(target)

        while (!queue.isEmpty()) {

            val eDeps = deps[queue.poll()]
            if (eDeps != null) {
                for (dep in eDeps) {
                    if (!explored.contains(dep)) queue.offer(dep)
                    explored.add(dep)
                }
            }
        }

        explored.remove(target)

        return explored.toList()
    }
}

/*
Реализовать структуру данных MaxStack, в которой есть методы:
pop() – удаляет и возвращает последний добавленный элемент за O(1),
  кидает исключение или возвращает ошибку, если стек пустой
push(value) – добавляет элемент в стек за O(1)
max() – возвращает максимальное значение среди всех элементов стека за O(1),
  кидает исключение или возвращает ошибку, если стек пустой
*/

class Solution2 {

    class MaxStack<T : Comparable<T>> {

        val realStack: Stack<T> = Stack()
        val maxValues: Stack<T> = Stack()

        fun push(value: T) {
            realStack.push(value)
            maxValues.push(maxOf(value, maxValues.peek() ?: value))
        }

        fun pop(): T {
            maxValues.pop() ?: throw IllegalStateException("Stack is empty")
            return realStack.pop()
        }

        fun max(): T {
            return maxValues.peek() ?: throw IllegalStateException("Stack is empty")
        }
    }

}

/*

Представьте что вы поддерживаете высоконагруженную систему.
Требуется написать 1 компонент — функцию которая будет ограничивать RPS запросов в API.
Эта функция вызывается при каждом запросе к API.
Функция должна либо возвращать OK (тогда запрос будет обработан) либо вернуть ошибку (тогда запрос не будет обработан).
Требуется написать эту функцию так, чтобы описанная схема гарантировала,
что ни за какой промежуток времени длиной в 1 секунду не было сделано более RPS запросов.
RPS может задаваться в промежутке от 1 до 100000.
От вас ожидается код этой функции и несколько тестов, демонстрирующих ее работоспособность.
*/

class Solution3(
    private val rps: Int,
) {

    private val list = LinkedList<Long>()
    private val interval = 1000L

    fun process(): Boolean {
        val time = Instant.now().toEpochMilli()
        val dropBefore = time - interval

        while ((list.peek() ?: time) < dropBefore) {
            list.pollFirst()
        }

        if (list.size >= rps) return false

        list.add(time)
        return true
    }
}

class Solution3Test() {

    val rps = 100
    val testService = Solution3(rps)

    @Test
    fun testPass() {

        var expected = rps

        for (i in 1..rps) {
            if (testService.process()) {
                expected--
            }
        }

        assertEquals(0, expected)
    }

    @Test
    fun testBlock() {

        val overRps = rps + 1
        var expected = rps

        for (i in 1..overRps) {
            if (testService.process()) {
                expected--
            }
        }

        assertEquals(0, expected)
    }

    @Test
    fun testPassClean() {

        var expected = rps

        for (i in 1..rps) {
            if (testService.process()) {
                expected--
            }
        }

        assertEquals(0, expected)

        Thread.sleep(1200L)

        expected = rps

        for (i in 1..rps) {
            if (testService.process()) {
                expected--
            }
        }

        assertEquals(0, expected)
    }
}
