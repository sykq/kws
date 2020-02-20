package io.psc.recommendation

import java.util.function.BiFunction

interface EvaluationFunction<T> : BiFunction<SpecificationAttribute<T>, SpecificationAttribute<T>, Int> {
    override fun apply(inputAttribute: SpecificationAttribute<T>, targetAttribute: SpecificationAttribute<T>): Int
}
