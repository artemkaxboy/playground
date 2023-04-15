package chapter4

class WeightedEdge(
        /**
         * From
         */
        u: Int,
        /**
         * Where
         */
        v: Int,
        val weight: Double,
) : Edge(u, v), Comparable<WeightedEdge> {

    override fun reversed(): WeightedEdge {
        return WeightedEdge(v, u, weight)
    }

    override fun compareTo(other: WeightedEdge): Int {
        val mine = weight
        val theirs = other.weight
        return mine.compareTo(theirs)
    }

    override fun toString(): String {
        return "$u $weight> $v"
    }
}
