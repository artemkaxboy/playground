package chapter5

import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.util.*
import java.util.zip.GZIPOutputStream
import kotlin.random.Random

class ListCompression(
    list: List<String>
) : Chromosome<ListCompression>() {

    private val myList: List<String> = list.toMutableList()

    private fun bytesCompressed(): Int {
        return runCatching {
            val baos = ByteArrayOutputStream()
            val gos = GZIPOutputStream(baos)
            val oos = ObjectOutputStream(gos)
            oos.writeObject(myList)
            oos.close()
            baos.size()
        }.getOrElse {
            System.err.println("Could not compress list!")
            it.printStackTrace()
            0
        }
    }

    override fun fitness(): Double = 1.0 / bytesCompressed()

    override fun mutate() {
        val idx1 = Random.nextInt(myList.size)
        val idx2 = Random.nextInt(myList.size)
        Collections.swap(myList, idx1, idx2)
    }

    override fun copy(): ListCompression {
        return ListCompression(myList.toMutableList())
    }

    override fun crossover(other: ListCompression): List<ListCompression> {
        val child1 = ListCompression(myList.toMutableList())
        val child2 = ListCompression(myList.toMutableList())
        val idx1 = Random.nextInt(myList.size)
        val idx2 = Random.nextInt(other.myList.size)
        val s1 = myList[idx1]
        val s2 = other.myList[idx2]

        val idx3 = myList.indexOf(s2)
        val idx4 = other.myList.indexOf(s1)
        Collections.swap(child1.myList, idx1, idx3)
        Collections.swap(child2.myList, idx2, idx4)
        return listOf(child1, child2)
    }

    override fun toString(): String {
        return "Order: $myList Bytes: ${bytesCompressed()}"
    }

    companion object {

        private val originalList = listOf(
            "Michael", "Sarah", "Joshua", "Narine", "David", "Sajid", "Melanie", "Daniel", "Wei", "Dean", "Brian",
            "Murat", "Lisa"
        )

        fun randomInstance(): ListCompression {
            return ListCompression(originalList.shuffled())
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val originalOrder = ListCompression(originalList)
            println(originalOrder)

            val initialPopulation = mutableListOf<ListCompression>()
            val populationSize = 100
            val generations = 15
            val threshold = 1.0

            for (i in 1..populationSize) {
                initialPopulation.add(randomInstance())
            }

            val ga = GeneticAlgorithm(initialPopulation, 0.2, 0.7, GeneticAlgorithm.SelectionType.TOURNAMENT_EX)
            val result = ga.run(generations, threshold)
            println(result)
        }
    }
}
