package chapter6

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
