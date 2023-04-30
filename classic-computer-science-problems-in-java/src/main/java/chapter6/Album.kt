package chapter6

import java.awt.Color

class Album(
    private val name: String,
    private val year: Int,
    length: Double,
    tracks: Double,
) : DataPoint(listOf(length, tracks)) {

    override fun toString(): String {
        return "($name, $year)"
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val albums = listOf(
                Album("Got to Be There", 1972, 35.45, 10.0),
                Album("Ben", 1972, 31.31, 10.0),
                Album("Music & Me", 1973, 32.09, 10.0),
                Album("Forever, Michael", 1975, 33.36, 10.0),
                Album("Off the Wall", 1979, 42.28, 10.0),
                Album("Thriller", 1982, 42.19, 9.0),
                Album("Bad", 1987, 48.16, 10.0),
                Album("Dangerous", 1991, 77.03, 14.0),
                Album("HIStory: Past, Present and Future, Book I", 1995, 148.58, 30.0),
                Album("Invincible", 2001, 77.05, 16.0),
            )
            val kMeans = KMeans(2, albums)
            val clusters = kMeans.run(100)

            val canvas = Canvas(
                640, 480,
                albums.minOf { it.dimensions[0] }.rangeTo(albums.maxOf { it.dimensions[0] }),
                albums.minOf { it.dimensions[1] }.rangeTo(albums.maxOf { it.dimensions[1] }),
            )

            val colors = listOf(Color.GREEN, Color.MAGENTA, Color.BLUE, Color.RED)
            for (clusterIndex in clusters.indices) {

                val color = colors[clusterIndex]
                val point = clusters[clusterIndex].centroid
                canvas.paintCentroid(point.dimensions[0], point.dimensions[1], color)

                for (p in clusters[clusterIndex].points) {
                    canvas.paintPoint(p.dimensions[0], p.dimensions[1], color)
                }
            }

            for (clusterIndex in clusters.indices) {
                println(
                    "Cluster $clusterIndex " +
                            "Avg Length ${clusters[clusterIndex].centroid.dimensions[0]} " +
                            "Avg Tracks ${clusters[clusterIndex].centroid.dimensions[1]}: " +
                            "${clusters[clusterIndex].points}"
                )
            }
        }
    }
}
