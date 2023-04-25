package chapter5

import kotlin.random.Random

class SimpleEquation(
    private var x: Int,
    private var y: Int,
) : Chromosome<SimpleEquation>() {

    override fun fitness(): Double {
        return (6 * x - x * x + 4 * y - y * y).toDouble()
    }

    override fun mutate() {
        if (Random.nextDouble() > 0.5) {
            if (Random.nextDouble() > 0.5) {
                x += 1
            } else {
                x -= 1
            }
        } else {
            if (Random.nextDouble() > 0.5) {
                y += 1
            } else {
                y -= 1
            }
        }
    }

    override fun copy(): SimpleEquation {
        return SimpleEquation(x, y)
    }

    override fun crossover(other: SimpleEquation): List<SimpleEquation> {
        val child1 = SimpleEquation(x, other.y)
        val child2 = SimpleEquation(other.x, y)
        return listOf(child1, child2)
    }

    override fun toString(): String {
        return "X: $x Y: $y Fitness: ${fitness()}"
    }

    companion object {

        const val MAX_START = 100

        fun randomInstance(): SimpleEquation {
            return SimpleEquation(Random.nextInt(MAX_START), Random.nextInt(MAX_START))
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val initialPopulation = mutableListOf<SimpleEquation>()
            val populationSize = 20
            val generations = 100
            val threshold = 13.0

            for (i in 1..populationSize) {
                initialPopulation.add(randomInstance())
            }

            val ga = GeneticAlgorithm(initialPopulation, 0.1, 0.7, GeneticAlgorithm.SelectionType.TOURNAMENT)
            val result = ga.run(generations, threshold)
            println("$result")
        }
    }
}
