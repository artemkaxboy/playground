package chapter6

import kotlin.math.pow
import kotlin.math.sqrt

open class DataPoint(
    initials: List<Double>
) {

    private val originals = initials
    var dimensions = initials.toMutableList()
    val numDimensions = dimensions.size

    fun distance(other: DataPoint): Double {
        var differences = 0.0
        for (i in 0 until numDimensions) {
            val diff = dimensions[i] - other.dimensions[i]
            differences += diff.pow(2)
        }
        return sqrt(differences)
    }

    override fun toString(): String {
        return originals.toString()
    }
}
