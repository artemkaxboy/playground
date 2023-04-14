package chapter4

open class Edge(
        val u: Int,     // from
        val v: Int,     // where
) {

    fun reversed(): Edge {
        return Edge(v, u)
    }

    override fun toString(): String {
        return "$u -> $v"
    }
}
