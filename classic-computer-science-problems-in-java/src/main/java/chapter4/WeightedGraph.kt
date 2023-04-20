package chapter4

import java.util.*
import java.util.function.Consumer

class WeightedGraph<V>(vertices: List<V>) : Graph<V, WeightedEdge>(vertices) {

    fun addEdge(edge: WeightedEdge) {
        edges[edge.u].add(edge)
        edges[edge.v].add(edge.reversed())
    }

    fun addEdge(u: Int, v: Int, weight: Double) {
        addEdge(WeightedEdge(u, v, weight))
    }

    fun addEdge(first: V, second: V, weight: Double) {
        addEdge(indexOf(first), indexOf(second), weight)
    }

    fun removeEdge(u: Int, v: Int) {
        edges[u].removeIf { it.v == v }
        edges[v].removeIf { it.u == u }
    }

    fun removeEdge(edge: WeightedEdge) {
        removeEdge(edge.u, edge.v)
    }

    fun removeEdge(first: V, second: V) {
        removeEdge(indexOf(first), indexOf(second))
    }

    fun mst(start: Int): List<WeightedEdge> {
        val result = LinkedList<WeightedEdge>()
        val vertexCount = getVertexCount()

        if (start < 0 || start > (vertexCount - 1)) return result

        val pq = PriorityQueue<WeightedEdge>()
        val visited = BooleanArray(vertexCount)

        val visit = Consumer<Int> { index ->
            visited[index] = true
            for (edge in edgesOf(index)) {
                if (!visited[edge.v]) {
                    pq.offer(edge)
                }
            }
        }

        visit.accept(start)
        while (pq.isNotEmpty()) {
            val edge = pq.poll()
            if (visited[edge.v]) continue // never check twice
            result.add(edge)
            visit.accept(edge.v)
        }

        return result
    }

    fun printWeightedPath(wp: List<WeightedEdge>) {
        wp.forEach { edge ->
            println("${vertexAt(edge.u)} ${edge.weight}> ${vertexAt(edge.v)}")
        }
        println("Total Weight: ${totalWeight(wp)}")
    }

    fun dijkstra(root: V): DijkstraResult {
        val first = indexOf(root)
        val vertexCount = getVertexCount()
        val distances = DoubleArray(vertexCount).apply { this[first] = 0.0 }
        val visited = BooleanArray(vertexCount).apply { this[first] = true }

        val pathMap = HashMap<Int, WeightedEdge>()
        val pq = PriorityQueue<DijkstraNode>()
        pq.offer(DijkstraNode(first, 0.0))

        while (pq.isNotEmpty()) {
            val u = pq.poll().vertex
            val distU = distances[u]

            edgesOf(u).forEach { we ->
                val distV = distances[we.v]
                val pathWeight = we.weight + distU
                if (!visited[we.v] || (distV > pathWeight)) {
                    visited[we.v] = true
                    distances[we.v] = pathWeight
                    pathMap.put(we.v, we)
                    pq.offer(DijkstraNode(we.v, pathWeight))
                }
            }
        }

        return DijkstraResult(distances, pathMap)
    }

    fun distanceArrayToDistanceMpa(distances: DoubleArray): Map<V, Double> {
        val distanceMap = HashMap<V, Double>(distances.size)
        for (i in distances.indices) {
            distanceMap[vertexAt(i)] = distances[i]
        }
        return distanceMap
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until getVertexCount()) {
            sb.append(vertexAt(i))
            sb.append(" -> ")
            sb.append(edgesOf(i).joinToString(", ", "[", "]") { we ->
                "(${vertexAt(we.v)}, ${we.weight})"
            })
            sb.append(System.lineSeparator())
        }
        return sb.toString()
    }

    companion object {

        fun totalWeight(path: List<WeightedEdge>): Double {
            return path.sumOf { it.weight }
        }

        fun pathMapToPath(start: Int, end: Int, pathMap: Map<Int, WeightedEdge>): List<WeightedEdge> {
            if (pathMap.isEmpty()) return emptyList()
            val path = LinkedList<WeightedEdge>()
            var edge = pathMap[end]!!
            path.add(edge)
            while (edge.u != start) {
                edge = pathMap[edge.u]!!
                path.add(edge)
            }
            return path.asReversed()
        }
    }

    data class DijkstraNode(
        val vertex: Int,
        val distance: Double,
    ) : Comparable<DijkstraNode> {

        override fun compareTo(other: DijkstraNode): Int {
            val mine = distance
            val theirs = other.distance
            return mine.compareTo(theirs)
        }
    }

    data class DijkstraResult(
        val distances: DoubleArray,
        val pathMap: Map<Int, WeightedEdge>,
    )
}

fun main() {
    val cityGraph2 = WeightedGraph(
        listOf(
            "Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago",
            "Boston", "New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit",
            "Philadelphia", "Washington"
        )
    )
    cityGraph2.addEdge("Seattle", "Chicago", 1737.0)
    cityGraph2.addEdge("Seattle", "San Francisco", 678.0)
    cityGraph2.addEdge("San Francisco", "Riverside", 386.0)
    cityGraph2.addEdge("San Francisco", "Los Angeles", 348.0)
    cityGraph2.addEdge("Los Angeles", "Riverside", 50.0)
    cityGraph2.addEdge("Los Angeles", "Phoenix", 357.0)
    cityGraph2.addEdge("Riverside", "Phoenix", 307.0)
    cityGraph2.addEdge("Riverside", "Chicago", 1704.0)
    cityGraph2.addEdge("Phoenix", "Dallas", 887.0)
    cityGraph2.addEdge("Phoenix", "Houston", 1015.0)
    cityGraph2.addEdge("Dallas", "Chicago", 805.0)
    cityGraph2.addEdge("Dallas", "Atlanta", 721.0)
    cityGraph2.addEdge("Dallas", "Houston", 225.0)
    cityGraph2.addEdge("Houston", "Atlanta", 702.0)
    cityGraph2.addEdge("Houston", "Miami", 968.0)
    cityGraph2.addEdge("Atlanta", "Chicago", 588.0)
    cityGraph2.addEdge("Atlanta", "Washington", 543.0)
    cityGraph2.addEdge("Atlanta", "Miami", 604.0)
    cityGraph2.addEdge("Miami", "Washington", 923.0)
    cityGraph2.addEdge("Chicago", "Detroit", 238.0)
    cityGraph2.addEdge("Detroit", "Boston", 613.0)
    cityGraph2.addEdge("Detroit", "Washington", 396.0)
    cityGraph2.addEdge("Detroit", "New York", 482.0)
    cityGraph2.addEdge("Boston", "New York", 190.0)
    cityGraph2.addEdge("New York", "Philadelphia", 81.0)
    cityGraph2.addEdge("Philadelphia", "Washington", 123.0)
    cityGraph2.removeEdge("Los Angeles", "Riverside")

    val mst = cityGraph2.mst(0)
    cityGraph2.printWeightedPath(mst)

    val srcVertex = "Los Angeles"
    val dstVertex = "Boston"
    val dijkstraResult = cityGraph2.dijkstra(srcVertex)
    val nameDistance = cityGraph2.distanceArrayToDistanceMpa(dijkstraResult.distances)
    println("${System.lineSeparator()}Distances from $srcVertex:")
    nameDistance.forEach { (name, distance) -> println("$name : $distance") }

    println("${System.lineSeparator()}Shortest path from $srcVertex to $dstVertex:")
    val path = WeightedGraph.pathMapToPath(
        cityGraph2.indexOf(srcVertex), cityGraph2.indexOf(dstVertex), dijkstraResult.pathMap
    )
    cityGraph2.printWeightedPath(path)
}
