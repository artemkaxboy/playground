package chapter5

abstract class Chromosome<T : Chromosome<T>> : Comparable<T> {

    abstract fun fitness(): Double

    abstract fun crossover(other: T): List<T>

    abstract fun mutate()

    abstract fun copy(): T

    override fun compareTo(other: T): Int {
        val mine = this.fitness()
        val theirs = other.fitness()
        return mine.compareTo(theirs)
    }
}
