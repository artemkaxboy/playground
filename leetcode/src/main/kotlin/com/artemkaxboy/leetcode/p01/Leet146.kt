package com.artemkaxboy.leetcode.p01

class Leet146 {

    private
    class LRUCache(val capacity: Int) {

        val setOfKeys = LinkedHashSet<Int>()
        val cache = HashMap<Int, Int>()

        fun get(key: Int): Int {
            return cache[key]
                ?.also { setOfKeys.remove(key) }
                ?.also { setOfKeys.add(key) }
                ?: -1
        }

        fun put(key: Int, value: Int) {
            setOfKeys.remove(key)
            if (setOfKeys.size >= capacity) {
                val outdated = setOfKeys.first()
                setOfKeys.remove(outdated)
                cache.remove(outdated)
            }
            cache[key] = value
            setOfKeys.add(key)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cache = LRUCache(2)
            cache.put(1, 1)
            cache.put(2, 2)
            println(cache.get(1))
            cache.put(3, 3)
            println(cache.get(2))
            cache.put(4, 4)
            println(cache.get(1))
            println(cache.get(3))
            println(cache.get(4))
        }
    }
}
