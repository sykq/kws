package io.psc.recommendation3.evaluation

import io.psc.recommendation3.BigDecimalSpecificationAttribute
import io.psc.recommendation3.PeriodSpecificationAttribute
import io.psc.recommendation3.StringListSpecificationAttribute
import io.psc.recommendation3.StringSpecificationAttribute

interface EvaluationVisitor<in O> {
    fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute, weight: Int,
              evaluationOptions: O? = null): Int = 0

    fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
              weight: Int, evaluationOptions: O? = null): Int = 0

    fun visit(text: StringSpecificationAttribute, comparisonText: StringSpecificationAttribute,
              weight: Int, evaluationOptions: O? = null): Int = 0

    fun visit(texts: StringListSpecificationAttribute, comparisonTexts: StringListSpecificationAttribute,
              weight: Int, evaluationOptions: O? = null): Int = 0
}
