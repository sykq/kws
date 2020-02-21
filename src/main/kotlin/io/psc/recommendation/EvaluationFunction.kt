package io.psc.recommendation

import java.util.function.BiFunction

interface EvaluationFunction<T> : BiFunction<SpecificationAttribute<T>, SpecificationAttribute<T>, Int> {
    companion object {
        const val weight = 100
    }

    val weight: Int
        get() = EvaluationFunction.weight

    override fun apply(inputAttribute: SpecificationAttribute<T>, targetAttribute: SpecificationAttribute<T>): Int
}
