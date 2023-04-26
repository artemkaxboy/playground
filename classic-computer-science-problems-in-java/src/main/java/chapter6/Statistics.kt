package chapter6

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class Statistics(
    private val list: List<Double>,
) {

    val dss = DoubleSummaryStatistics().apply { list.forEach { d -> this.accept(d) } }

    fun sum() = dss.sum

    // Найдите среднее значение (mean)
    fun mean() = dss.average

    // Найдите сумму дисперсии ((Xi — среднее) ^ 2) / N
    fun variance(): Double {
        val mean = mean()
        return list.asSequence()
            .map { x -> (x - mean).pow(2) }
            .average()
    }

    // Найдите стандартное отклонение sqrt (дисперсия)
    fun std() = sqrt(variance())

    // Преобразование элементов в соответствующие z-значения
    // (формула z-score = (x-mean) / std)
    fun zscored(): List<Double> {
        val mean = mean()
        val std = std()
        return list.map { x -> if (std != 0.0) (x - mean) / std else 0.0 }
    }

    fun max() = dss.max
    fun min() = dss.min
}
