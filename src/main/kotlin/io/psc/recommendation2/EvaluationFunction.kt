package io.psc.recommendation2

import java.util.function.BiFunction

interface EvaluationFunction<T> : BiFunction<T, T, Int> {
    companion object {
        const val weight = 100
    }

    val weight: Int
        get() = EvaluationFunction.weight

    var evaluationVisitor: EvaluationVisitor?
        get() = evaluationVisitor!!
        set(value) {this.evaluationVisitor = value}

    override fun apply(inputAttribute: T, targetAttribute: T): Int

}

class PeriodEvaluationFunction(
        override var evaluationVisitor: EvaluationVisitor?) : EvaluationFunction<PeriodSpecificationAttribute> {
    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                       targetAttribute: PeriodSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}


class BigDecimalEvaluationFunction(
        override var evaluationVisitor: EvaluationVisitor?) : EvaluationFunction<BigDecimalSpecificationAttribute> {
    override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                       targetAttribute: BigDecimalSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}


class StringListEvaluationFunction(
        override var evaluationVisitor: EvaluationVisitor?) : EvaluationFunction<StringListSpecificationAttribute> {
    override fun apply(inputAttribute: StringListSpecificationAttribute,
                       targetAttribute: StringListSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}

class StringEvaluationFunction(
        override var evaluationVisitor: EvaluationVisitor?) : EvaluationFunction<StringSpecificationAttribute> {
    override fun apply(inputAttribute: StringSpecificationAttribute,
                       targetAttribute: StringSpecificationAttribute): Int {
        return evaluationVisitor!!.visit(inputAttribute, targetAttribute, weight)
    }
}
