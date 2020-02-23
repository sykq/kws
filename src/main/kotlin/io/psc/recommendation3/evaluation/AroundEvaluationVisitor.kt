package io.psc.recommendation3.evaluation

import io.psc.recommendation3.PeriodSpecificationAttribute
import java.time.Period

class AroundEvaluationVisitor<T> : EvaluationVisitor<AroundEvaluationOptions<T>> {
    override fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute,
                       weight: Int, evaluationOptions: AroundEvaluationOptions<T>?): Int {
        return if (evaluationOptions != null) {
            val lowerBoundary = period.value.minus(evaluationOptions.minus as Period).minus(comparisonPeriod.value)
            val upperBoundary = period.value.plus(evaluationOptions.plus as Period).minus(comparisonPeriod.value)
            if (lowerBoundary.isNegative && !upperBoundary.isNegative && !upperBoundary.isZero)
                weight
            else
                0
        } else 0
    }
}

data class AroundEvaluationOptions<T>(val minus: T, val plus: T)