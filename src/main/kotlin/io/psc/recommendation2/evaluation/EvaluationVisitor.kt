package io.psc.recommendation2.evaluation

import io.psc.recommendation2.BigDecimalSpecificationAttribute
import io.psc.recommendation2.PeriodSpecificationAttribute
import io.psc.recommendation2.StringListSpecificationAttribute
import io.psc.recommendation2.StringSpecificationAttribute

interface EvaluationVisitor<in T> {
    fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute, weight: Int,
              evaluationOptions: T? = null): Int = 0

    fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
              weight: Int, evaluationOptions: T? = null): Int = 0

    fun visit(text: StringSpecificationAttribute, comparisonText: StringSpecificationAttribute,
              weight: Int, evaluationOptions: T? = null): Int = 0

    fun visit(texts: StringListSpecificationAttribute, comparisonTexts: StringListSpecificationAttribute,
              weight: Int, evaluationOptions: T? = null): Int = 0
}