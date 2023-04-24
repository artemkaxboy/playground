package chapter5;

import kotlin.random.Random

class GeneticAlgorithm<C : Chromosome<C>>(
    initialPopulation: List<C>,
    private val mutationChance: Double,
    private val crossoverChance: Double,
    private val selectionType: SelectionType,
) {

    private var population: ArrayList<C> = ArrayList(initialPopulation)

    private fun pickRoulette(wheel: DoubleArray, numPicks: Int): List<C> {
        val picks = ArrayList<C>()
        for (i in 0 until numPicks) {
            var pick = Random.nextDouble()
            for (j in wheel.indices) {
                pick -= wheel[j]
                if (pick <= 0) {
                    picks.add(population.get(j))
                    break
                }
            }
        }
        return picks
    }

    private fun pickTournament(numParticipants: Int, numPicks: Int): List<C> {
        return population.apply { shuffle() }
            .take(numParticipants)
            .sortedDescending()
            .take(numPicks)
    }

    private fun reproduceAndReplace() {
        val nextPopulation = ArrayList<C>()
        while (nextPopulation.size < population.size) {
            val parents = when (selectionType) {
                SelectionType.ROULETTE -> {
                    val totalFitness = population.sumOf { it.fitness() }
                    val wheel = population.map { it.fitness() / totalFitness }.toDoubleArray()
                    pickRoulette(wheel, 2)
                }

                SelectionType.TOURNAMENT -> {
                    pickTournament(population.size / 2, 2)
                }
            }

            if (Random.nextDouble() < crossoverChance) {
                val parent1 = parents[0]
                val parent2 = parents[1]
                nextPopulation.addAll(parent1.crossover(parent2))
            } else {
                nextPopulation.addAll(parents)
            }
        }

        if (nextPopulation.size > population.size) nextPopulation.removeAt(0)

        population = nextPopulation
    }

    private fun mutate() {
        population.forEach { individual ->
            if (Random.nextDouble() < mutationChance) {
                individual.mutate()
            }
        }
    }

    fun run(maxGenerations: Int, threshold: Double): C {
        var best = population.max().copy()

        for (generation in 0 until maxGenerations) {

            if (best.fitness() >= threshold) {
                return best
            }

            println("Generation $generation Best ${best.fitness()} Avg ${population.map { it.fitness() }.average()}")
            reproduceAndReplace()
            mutate()

            best = population.max().coerceAtLeast(best)
        }

        return best
    }

    enum class SelectionType {
        ROULETTE,
        TOURNAMENT,
    }
}
