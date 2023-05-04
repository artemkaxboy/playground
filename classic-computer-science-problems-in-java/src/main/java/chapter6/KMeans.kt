package chapter6

import kotlin.random.Random

class KMeans<Point : DataPoint>(
    k: Int? = null,
    initCentroids: List<Point>? = null,
    private val points: List<Point>,
) {

    val clusters = mutableListOf<Cluster>()

    init {
        val centroids = if (k != null) {
            if (k < 1) {
                throw IllegalArgumentException("k must be >= 1")
            }
            (1..k).map { randomPoint() }
        } else initCentroids
            ?: throw IllegalArgumentException("k must be >= 1 or initCentroids not empty")

        zScoreNormalize()
        for (c in centroids) {
            val cluster = Cluster(mutableListOf(), c)
            clusters.add(cluster)
        }
    }

    private fun centroids(): List<DataPoint> {
        return clusters.map { it.centroid }
    }

    private fun dimensionSlice(dimension: Int): List<Double> {
        return points.map { x -> x.dimensions[dimension] }
    }

    private fun zScoreNormalize() {
        val zscored = mutableListOf<MutableList<Double>>()
        for (point in points.indices) {
            zscored.add(mutableListOf())
        }

        val dimensions = points.first().numDimensions
        for (dimension in 0 until dimensions) {
            val dimensionSlice = dimensionSlice(dimension)
            val stats = Statistics(dimensionSlice)
            val zscores = stats.zscored()
            for (index in zscores.indices) {
                zscored[index].add(zscores[index])
            }
        }
        for (i in points.indices) {
            points[i].dimensions = zscored[i]
        }
    }

    private fun randomPoint(): DataPoint {
        val randDimensions = mutableListOf<Double>()
        for (dimension in 0 until points[0].numDimensions) {
            val values = dimensionSlice(dimension)
            val stats = Statistics(values)
            val randValue = Random.nextDouble(stats.min(), stats.max())
            randDimensions.add(randValue)
        }
        return DataPoint(randDimensions)
    }

    private fun assignClusters() {
        for (point in points) {
            var lowestDistance = Double.MAX_VALUE
            var closestCluster = clusters[0]
            for (cluster in clusters) {
                val centroidDistance = point.distance(cluster.centroid)
                if (centroidDistance < lowestDistance) {
                    lowestDistance = centroidDistance
                    closestCluster = cluster
                }
            }
            closestCluster.points.add(point)
        }
    }

    private fun generateCentroids() {
        for (cluster in clusters) {
            if (cluster.points.isEmpty()) {
                continue
            }

            val means = mutableListOf<Double>()
            for (i in 0 until cluster.points[0].numDimensions) {
                val dimension = i
                val dimensionMean = cluster.points.asSequence()
                    .map { it.dimensions[dimension] }
                    .average()
                means.add(dimensionMean)
            }
        }
    }

    private fun listsEqual(first: List<DataPoint>, second: List<DataPoint>): Boolean {
        if (first.size != second.size) {
            return false
        }
        for (i in first.indices) {
            for (j in 0 until first[0].numDimensions) {
                if (first[i].dimensions[j] != second[i].dimensions[j]) {
                    return false
                }
            }
        }
        return true
    }

    public fun run(maxIteration: Int): List<Cluster> {
        for (iteration in 0 until maxIteration) {
            for (cluster in clusters) {
                cluster.points.clear()
            }
            assignClusters()
            val oldCentroids = centroids().toMutableList()
            generateCentroids()
            if (listsEqual(oldCentroids, centroids())) {
                println("Converged after $iteration iterations")
                return clusters
            }
        }
        return clusters
    }

    inner class Cluster(
        val points: MutableList<Point>,
        randPoint: DataPoint,
    ) {
        val centroid = randPoint
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val point1 = DataPoint(listOf(2.0, 1.0, 1.0))
            val point2 = DataPoint(listOf(2.0, 2.0, 5.0))
            val point3 = DataPoint(listOf(3.0, 1.5, 2.5))

            val kmeansTest = KMeans(2, points = listOf(point1, point2, point3))
            val testClusters = kmeansTest.run(100)
            for (clusterIndex in testClusters.indices) {
                println("Cluster $clusterIndex: ${testClusters[clusterIndex].points}")
            }
        }
    }
}
