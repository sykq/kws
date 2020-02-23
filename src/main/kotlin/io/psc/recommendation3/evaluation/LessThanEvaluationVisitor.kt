package io.psc.recommendation3.evaluation

import io.psc.recommendation3.BigDecimalSpecificationAttribute
import io.psc.recommendation3.PeriodSpecificationAttribute

class LessThanEvaluationVisitor<T>: EvaluationVisitor<T> {
    override fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute,
                       weight: Int, evaluationOptions: T?): Int {
        return if (comparisonPeriod.value.minus(period.value).isNegative) weight else 0
    }

    override fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
                       weight: Int, evaluationOptions: T?): Int {
        return if(comparisonValue.value < value.value) weight else 0
    }

}