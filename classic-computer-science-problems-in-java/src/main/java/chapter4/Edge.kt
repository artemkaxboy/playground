package chapter4

open class Edge(
        /**
         * From
         */
        val u: Int,
        /**
         * Where
         */
        val v: Int,
) {

    open fun reversed(): Edge {
        return Edge(v, u)
    }

    override fun toString(): String {
        return "$u -> $v"
    }
}
