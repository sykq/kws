package io.psc.recommendation2

import io.psc.recommendation2.evaluation.EvaluationVisitor
import java.util.function.BiFunction

interface EvaluationFunction<T, O> : BiFunction<T, T, Int> {
    companion object {
        const val weight = 100
    }

    val weight: Int
        get() = EvaluationFunction.weight

    var evaluationVisitor: EvaluationVisitor<O>?
        get() = evaluationVisitor!!
        set(value) {
            this.evaluationVisitor = value
        }

    var evaluationOptions: O?
        get() = evaluationOptions!!
        set(value) {
            this.evaluationOptions = value
        }

    override fun apply(inputAttribute: T, targetAttribute: T): Int

}

class PeriodEvaluationFunction<T>(
        override var evaluationVisitor: EvaluationVisitor<T>?,
        override var evaluationOptions: T? = null) : EvaluationFunction<PeriodSpecificationAttribute, T> {
    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                       targetAttribute: PeriodSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight, evaluationOptions)
    }
}


class BigDecimalEvaluationFunction<T>(
        override var evaluationVisitor: EvaluationVisitor<T>?,
        override var evaluationOptions: T? = null) : EvaluationFunction<BigDecimalSpecificationAttribute, T> {
    override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                       targetAttribute: BigDecimalSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}


class StringListEvaluationFunction<T>(
        override var evaluationVisitor: EvaluationVisitor<T>?,
        override var evaluationOptions: T? = null) : EvaluationFunction<StringListSpecificationAttribute, T> {
    override fun apply(inputAttribute: StringListSpecificationAttribute,
                       targetAttribute: StringListSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}

class StringEvaluationFunction<T>(
        override var evaluationVisitor: EvaluationVisitor<T>?,
        override var evaluationOptions: T? = null) : EvaluationFunction<StringSpecificationAttribute, T> {
    override fun apply(inputAttribute: StringSpecificationAttribute,
                       targetAttribute: StringSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}
