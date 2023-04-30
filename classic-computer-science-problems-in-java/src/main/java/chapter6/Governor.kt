package chapter6

import java.awt.Color

class Governor(
    private val longitude: Double,
    private val age: Double,
    private val state: String,
) : DataPoint(listOf(longitude, age)) {

    override fun toString(): String {
        return "$state: (longitude: $longitude, age: $age)"
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val governors = listOf(
                Governor(-86.79113, 72.0, "Alabama"),
                Governor(-152.404419, 66.0, "Alaska"),
                Governor(-111.431221, 53.0, "Arizona"),
                Governor(-92.373123, 66.0, "Arkansas"),
                Governor(-119.681564, 79.0, "California"),
                Governor(-105.311104, 65.0, "Colorado"),
                Governor(-72.755371, 61.0, "Connecticut"),
                Governor(-75.507141, 61.0, "Delaware"),
                Governor(-81.686783, 64.0, "Florida"),
                Governor(-83.643074, 74.0, "Georgia"),
                Governor(-157.498337, 60.0, "Hawaii"),
                Governor(-114.478828, 75.0, "Idaho"),
                Governor(-88.986137, 60.0, "Illinois"),
                Governor(-86.258278, 49.0, "Indiana"),
                Governor(-93.210526, 57.0, "Iowa"),
                Governor(-96.726486, 60.0, "Kansas"),
                Governor(-84.670067, 50.0, "Kentucky"),
                Governor(-91.867805, 50.0, "Louisiana"),
                Governor(-69.381927, 68.0, "Maine"),
                Governor(-76.802101, 61.0, "Maryland"),
                Governor(-71.530106, 60.0, "Massachusetts"),
                Governor(-84.536095, 58.0, "Michigan"),
                Governor(-93.900192, 70.0, "Minnesota"),
                Governor(-89.678696, 62.0, "Mississippi"),
                Governor(-92.288368, 43.0, "Missouri"),
                Governor(-110.454353, 51.0, "Montana"),
                Governor(-98.268082, 52.0, "Nebraska"),
                Governor(-117.055374, 53.0, "Nevada"),
                Governor(-71.563896, 42.0, "New Hampshire"),
                Governor(-74.521011, 54.0, "New Jersey"),
                Governor(-106.248482, 57.0, "New Mexico"),
                Governor(-74.948051, 59.0, "New York"),
                Governor(-79.806419, 60.0, "North Carolina"),
                Governor(-99.784012, 60.0, "North Dakota"),
                Governor(-82.764915, 65.0, "Ohio"),
                Governor(-96.928917, 62.0, "Oklahoma"),
                Governor(-122.070938, 56.0, "Oregon"),
                Governor(-77.209755, 68.0, "Pennsylvania"),
                Governor(-71.51178, 46.0, "Rhode Island"),
                Governor(-80.945007, 70.0, "South Carolina"),
                Governor(-99.438828, 64.0, "South Dakota"),
                Governor(-86.692345, 58.0, "Tennessee"),
                Governor(-97.563461, 59.0, "Texas"),
                Governor(-111.862434, 70.0, "Utah"),
                Governor(-72.710686, 58.0, "Vermont"),
                Governor(-78.169968, 60.0, "Virginia"),
                Governor(-121.490494, 66.0, "Washington"),
                Governor(-80.954453, 66.0, "West Virginia"),
                Governor(-89.616508, 49.0, "Wisconsin"),
                Governor(-107.30249, 55.0, "Wyoming")
            )

            val kmeans = KMeans(2, governors)
            val govClusters = kmeans.run(100)

            val canvas = Canvas(
                640, 480,
                governors.minOf { it.dimensions[0] }.rangeTo(governors.maxOf { it.dimensions[0] }),
                governors.minOf { it.dimensions[1] }.rangeTo(governors.maxOf { it.dimensions[1] }),
            )

            val colors = listOf(Color.GREEN, Color.MAGENTA, Color.BLUE, Color.RED)
            for (clusterIndex in govClusters.indices) {

                val color = colors[clusterIndex]
                val point = govClusters[clusterIndex].centroid
                canvas.paintCentroid(point.dimensions[0], point.dimensions[1], color)

                for (p in govClusters[clusterIndex].points) {
                    canvas.paintPoint(p.dimensions[0], p.dimensions[1], color)
                }
            }
        }
    }
}
