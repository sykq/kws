package io.psc.recommendation3

import io.psc.recommendation3.evaluation.EvaluationVisitor
import java.util.function.BiFunction

interface EvaluationFunction<T, O> : BiFunction<T, T, Int> {
    companion object {
        const val weight = 100
    }

    val weight: Int
        get() = EvaluationFunction.weight

    val evaluationVisitor: EvaluationVisitor<O>?
        get() = null

    val evaluationOptions: O?
        get() = null

    override fun apply(inputAttribute: T, targetAttribute: T): Int

}

class PeriodEvaluationFunction<O>(
        override var evaluationVisitor: EvaluationVisitor<O>?,
        override var evaluationOptions: O? = null) : EvaluationFunction<PeriodSpecificationAttribute, O> {
    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                       targetAttribute: PeriodSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight, evaluationOptions)
    }
}


class BigDecimalEvaluationFunction<O>(
        override var evaluationVisitor: EvaluationVisitor<O>?,
        override var evaluationOptions: O? = null) : EvaluationFunction<BigDecimalSpecificationAttribute, O> {
    override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                       targetAttribute: BigDecimalSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight, evaluationOptions)
    }
}


class StringListEvaluationFunction<O>(
        override var evaluationVisitor: EvaluationVisitor<O>?,
        override var evaluationOptions: O? = null) : EvaluationFunction<StringListSpecificationAttribute, O> {
    override fun apply(inputAttribute: StringListSpecificationAttribute,
                       targetAttribute: StringListSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight, evaluationOptions)
    }
}

class StringEvaluationFunction<O>(
        override var evaluationVisitor: EvaluationVisitor<O>?,
        override var evaluationOptions: O? = null) : EvaluationFunction<StringSpecificationAttribute, O> {
    override fun apply(inputAttribute: StringSpecificationAttribute,
                       targetAttribute: StringSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight, evaluationOptions)
    }
}
