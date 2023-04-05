package com.artemkaxboy.leetcode.p04

import java.util.TreeMap

private class Leet435v2 {

    val knownOverlaps = TreeMap<Int, Int>()
    val filteredOutKeys = HashSet<Int>()

    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        knownOverlaps.clear()
        filteredOutKeys.clear()
        for (i in intervals.indices) {
            calcAndSaveOverlaps(intervals, i)
        }
        println("Known overlaps: $knownOverlaps")
        while (consumeOneOverlap(intervals)) {
        }

        return filteredOutKeys.size
    }

    private fun consumeOneOverlap(intervals: Array<IntArray>): Boolean {
        val keyToConsume = knownOverlaps.keys.firstOrNull() ?: return false
        if (knownOverlaps[keyToConsume]!! > 0) {
            val intervalToRemove = intervals.withIndex()
                .filter { !filteredOutKeys.contains(it.index) }
                .filter { keyToConsume >= it.value[0] && keyToConsume < it.value[1] }
                .maxByOrNull { it.value[1] }

            intervalToRemove?.let { removeInterval(it.value[0], it.value[1]) }
            intervalToRemove?.let { filteredOutKeys.add(it.index) }
        } else {
            knownOverlaps.remove(keyToConsume)
        }
        return true
    }

    private fun removeInterval(start: Int, end: Int) {
        println("Remove interval $start - $end")
        knownOverlaps.tailMap(start).headMap(end).keys.forEach {
            val newValue = knownOverlaps[it]!! - 1
            knownOverlaps[it] = newValue
        }
    }

    private fun saveOverlap(start: Int, size: Int) {
        knownOverlaps[start] = (knownOverlaps.floorEntry(start)?.value ?: 0)
        knownOverlaps.tailMap(start).headMap(start + size).keys.forEach { knownOverlaps[it] = knownOverlaps[it]!! + 1 }
        val end = start + size
        if (!knownOverlaps.contains(end)) // if the next key after range already has value do nothing
            knownOverlaps[end] = knownOverlaps.floorEntry(end).value - 1
    }

    private fun calcAndSaveOverlaps(allIntervals: Array<IntArray>, currentIntervalNumber: Int) {
        val overlapsForInterval = TreeMap<Int, Int>()
        for (i in 0 until currentIntervalNumber) {
            val anOverlap = calcOverlap(
                allIntervals[i][0],
                allIntervals[i][1],
                allIntervals[currentIntervalNumber][0],
                allIntervals[currentIntervalNumber][1]
            )
            anOverlap?.let {
                overlapsForInterval[it.first] = maxOf(it.second, overlapsForInterval[it.first] ?: 0)
            }
        }
        val mergedOverlaps = mergeOverlaps(overlapsForInterval)
        saveOverlaps(mergedOverlaps)
//        println(mergedOverlaps)
    }

    private fun saveOverlaps(oneRangeOverlaps: TreeMap<Int, Int>) {
        oneRangeOverlaps.forEach { (t, u) ->
            saveOverlap(t, u)
        }
    }

    private fun mergeOverlaps(knownIntervals: TreeMap<Int, Int>): TreeMap<Int, Int> {
        val cleanTree = TreeMap<Int, Int>()
        var lastKey = 0
        knownIntervals.entries.firstOrNull()?.let {
            cleanTree[it.key] = it.value
            lastKey = it.key
        }
        knownIntervals.entries.zipWithNext().forEach {
            val lastValue = cleanTree[lastKey]!!
            if (lastKey + lastValue >= it.second.key) {
                cleanTree[lastKey] =
                    maxOf(cleanTree[lastKey]!!, it.second.value + (it.second.key - lastKey))
            } else {
                cleanTree[it.second.key] = it.second.value
                lastKey = it.second.key
            }
        }
        return cleanTree
    }

    private fun calcOverlap(start1: Int, end1: Int, start2: Int, end2: Int): Pair<Int, Int>? {
        if (start1 >= start2 && start1 < end2) {
            val size = minOf(end1, end2) - start1
            return start1 to size
        }
        if (start2 >= start1 && start2 < end1) {
            val size = minOf(end1, end2) - start2
            return start2 to size
        }
        return null
    }
}

fun main() {
    val solution = Leet435v2()

    val myTest = "[[1,10],[2,5],[4,11],[1,4]]" // 2
//    doWork(myTest, solution)

    val testCase1 = "[[1,2],[2,3],[3,4],[1,3]]" // 1
//    doWork(testCase1, solution)

    val testCase2 = "[[1,2],[1,2],[1,2]]" // 2
//    doWork(testCase2, solution)

    val testCase3 = "[[1,2],[2,3]]" // 0
//    doWork(testCase3, solution)

    val wrongAnswer1 = "[[0,2],[1,3],[2,4],[3,5],[4,6]]" // 2
//    doWork(wrongAnswer1, solution)

    val wrongAnswer2 = "[[-52,31],[-73,-26],[82,97],[-65,-11],[-62,-49],[95,99],[58,95],[-31,49],[66,98],[-63,2]," +
            "[30,47],[-40,-26]]" // 7
//    doWork(wrongAnswer2, solution)

    val wrongAnswer3 = "[[-36057,-16287],[-35323,-26257],[-27140,-14703],[-15279,21851],[-15129,-5773]," +
            "[-12098,16264],[-8144,1080],[-3035,30075],[1937,6906],[11834,20971],[19621,34415],[10508,46685]," +
            "[28565,37578],[32985,36313],[44578,45600],[47939,48626]]"
//    doWork(wrongAnswer3, solution)

    val nullPointer1 = "[[-25322,-4602],[-35630,-28832],[-33802,29009],[13393,24550],[-10655,16361],[-2835,10053]," +
            "[-2290,17156],[1236,14847],[-45022,-1296],[-34574,-1993],[-14129,15626],[3010,14502],[42403,45946]," +
            "[-22117,13380],[7337,33635],[-38153,27794],[47640,49108],[40578,46264],[-38497,-13790],[-7530,4977]," +
            "[-29009,43543],[-49069,32526],[21409,43622],[-28569,16493],[-28301,34058]]"
//    doWork(nullPointer1, solution)

    val wrongAnswer4 = "[[-70,27],[-41,11],[78,85],[-95,55],[-63,4],[-96,38],[33,65],[-16,38],[-43,15],[-69,-7]," +
            "[64,67],[-33,97],[58,74],[75,83],[87,94],[-64,20],[-77,-7],[48,65],[-80,3],[-10,61],[71,87],[75,82]," +
            "[-79,-34],[-67,50],[-13,4],[34,42],[-50,-12],[32,51],[-73,40],[18,87],[-16,74],[-27,75],[15,60]," +
            "[-15,63],[-70,57],[-6,57],[-77,85],[59,94],[38,73],[18,25],[-57,36],[88,95],[72,98],[38,40],[-73,9]," +
            "[-27,60],[79,92],[-77,47],[47,67],[86,96],[16,44],[37,54],[37,76],[-92,-81],[90,92],[77,84],[-88,5]," +
            "[26,64],[13,26],[-42,-36],[-96,60],[98,100],[92,94],[94,100],[63,70],[-41,-22],[6,38],[-53,-5]," +
            "[35,79],[49,50],[-46,-15],[90,93],[-45,63],[20,48],[-50,-30],[17,85],[-9,97],[-97,-12],[43,96]," +
            "[-4,64],[-34,60],[29,87],[-90,-59],[46,81],[-77,86],[56,86],[-30,-24],[-39,-37],[-17,44],[-40,-1]," +
            "[71,81]]" // 79
    doWork(wrongAnswer4, solution)
}

private fun doWork(data: String, service: Leet435v2): Int {
    return data.trim('[', ']').split("],[")
        .map { it.split(",").map { el -> el.toInt() } }
        .map { intArrayOf(it[0], it[1]) }
        .toTypedArray()
        .let { service.eraseOverlapIntervals(it) }
        .also { println("Result: $it\n") }
}
