package io.psc.recommendation2

import io.psc.recommendation2.BigDecimalSpecificationAttribute
import io.psc.recommendation2.PeriodSpecificationAttribute
import io.psc.recommendation2.StringListSpecificationAttribute
import io.psc.recommendation2.StringSpecificationAttribute

interface EvaluationVisitor {
    fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute, weight: Int): Int
    fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
              weight: Int): Int

    fun visit(text: StringSpecificationAttribute, comparisonText: StringSpecificationAttribute, weight: Int): Int
    fun visit(texts: StringListSpecificationAttribute, comparisonTexts: StringListSpecificationAttribute,
              weight: Int): Int
}

interface EvaluationFunctionVisitor {
    fun visit(periodEvaluationFunction: PeriodEvaluationFunction): Int
    fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
              weight: Int): Int

    fun visit(text: StringSpecificationAttribute, comparisonText: StringSpecificationAttribute, weight: Int): Int
    fun visit(texts: StringListSpecificationAttribute, comparisonTexts: StringListSpecificationAttribute): Int
}