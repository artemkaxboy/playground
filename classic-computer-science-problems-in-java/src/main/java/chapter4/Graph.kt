package chapter4

// V - type of vertices
// E - type of edges
abstract class Graph<V, E : Edge>(vertices: List<V> = emptyList()) {

    private val vertices = ArrayList(vertices)
    protected val edges = ArrayList<ArrayList<E>>()

    init {
        repeat(vertices.size) {
            edges.add(ArrayList())
        }
    }

    fun getVertexCount(): Int {
        return vertices.size
    }

    fun getEdgeCount(): Int {
        return edges.sumOf(ArrayList<out Any>::size)
    }

    fun addVertex(vertex: V): Int {
        vertices.add(vertex)
        edges.add(ArrayList())
        return getVertexCount() - 1
    }

    fun vertexAt(index: Int): V {
        return vertices[index]
    }

    fun indexOf(vertex: V): Int {
        return vertices.indexOf(vertex)
    }

    fun neighborsOf(index: Int): List<V> {
        return edges[index].map { vertexAt(it.v) }
    }

    fun neighborsOf(vertex: V): List<V> {
        return neighborsOf(indexOf(vertex))
    }

    fun edgesOf(index: Int): List<E> {
        return edges[index]
    }

    fun edgesOf(vertex: V): List<E> {
        return edgesOf(indexOf(vertex))
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in vertices.indices) {
            sb.append(vertexAt(i))
            sb.append(" -> ")
            sb.append(neighborsOf(i).joinToString(", ", "[", "]"))
            sb.append(System.lineSeparator())
        }
        return sb.toString()
    }
}
