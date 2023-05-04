package chapter6

import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

open class DataPoint(
    initials: List<Double>,
    private val id: String = "id",
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
        return "$id: $originals"
    }

    companion object {

        fun loadCsv(path: String): List<DataPoint> {

            return File(path).useLines { lines ->
                lines.drop(1)
                    .map { it.split("\t") }
                    .map { DataPoint(it.drop(1).map { v -> v.toDouble() }, it[0]) }
                    .toList()
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val albums = loadCsv("classic-computer-science-problems-in-java/src/main/java/chapter6/albums.csv")
            val kmeans = KMeans(2, points = albums)
            val govClusters = kmeans.run(100)
            for (clusterIndex in govClusters.indices) {
                println("Cluster $clusterIndex: ${govClusters[clusterIndex].points}")
            }
        }
    }
}
