package chapter5

import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class SendMoreMoney2(
    private val letters: List<Char>
) : Chromosome<SendMoreMoney2>() {

    override fun fitness(): Double {
        val s = letters.indexOf('S')
        val e = letters.indexOf('E')
        val n = letters.indexOf('N')
        val d = letters.indexOf('D')
        val m = letters.indexOf('M')
        val o = letters.indexOf('O')
        val r = letters.indexOf('R')
        val y = letters.indexOf('Y')
        val send = s * 1000 + e * 100 + n * 10 + d
        val more = m * 1000 + o * 100 + r * 10 + e
        val money = m * 10000 + o * 1000 + n * 100 + e * 10 + y
        val difference = ((send + more) - money).absoluteValue
        return 1.0 / (difference + 1.0)
    }

    override fun mutate() {
        val idx1 = Random.nextInt(letters.size)
        val idx2 = Random.nextInt(letters.size)
        Collections.swap(letters, idx1, idx2)
    }

    override fun copy(): SendMoreMoney2 {
        return SendMoreMoney2(letters.toMutableList())
    }

    override fun crossover(other: SendMoreMoney2): List<SendMoreMoney2> {
        val child1 = SendMoreMoney2(letters.toMutableList())
        val child2 = SendMoreMoney2(other.letters.toMutableList())
        val idx1 = Random.nextInt(letters.size)
        val idx2 = Random.nextInt(other.letters.size)
        val l1 = letters[idx1]
        val l2 = other.letters[idx2]
        val idx3 = letters.indexOf(l2)
        val idx4 = other.letters.indexOf(l1)
        Collections.swap(child1.letters, idx1, idx3)
        Collections.swap(child2.letters, idx2, idx4)
        return listOf(child1, child2)
    }

    override fun toString(): String {
        val s = letters.indexOf('S')
        val e = letters.indexOf('E')
        val n = letters.indexOf('N')
        val d = letters.indexOf('D')
        val m = letters.indexOf('M')
        val o = letters.indexOf('O')
        val r = letters.indexOf('R')
        val y = letters.indexOf('Y')
        val send = s * 1000 + e * 100 + n * 10 + d
        val more = m * 1000 + o * 100 + r * 10 + e
        val money = m * 10000 + o * 1000 + n * 100 + e * 10 + y
        val difference = ((send + more) - money).absoluteValue
        return "$send + $more = $money Difference: $difference"
    }

    companion object {

        fun randomInstance(): SendMoreMoney2 {
            val letters = "SENDMORY  ".toList().shuffled()
            return SendMoreMoney2(letters)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val initialPopulation = mutableListOf<SendMoreMoney2>()
            val populationSize = 1000
            val generations = 1000
            val threshold = 1.0
            for (i in 1..populationSize) {
                initialPopulation.add(randomInstance())
            }

            val ga = GeneticAlgorithm(initialPopulation, 0.2, 0.7, GeneticAlgorithm.SelectionType.TOURNAMENT)
            val result = ga.run(generations, threshold)
            println(result)
        }
    }
}
