package io.psc.recommendation2

class LessThanEvaluationVisitor : EvaluationVisitor {
    override fun visit(period: PeriodSpecificationAttribute, comparisonPeriod: PeriodSpecificationAttribute,
                       weight: Int): Int {
        return if (period.value.minus(comparisonPeriod.value).isNegative) 0 else weight
    }

    override fun visit(value: BigDecimalSpecificationAttribute, comparisonValue: BigDecimalSpecificationAttribute,
                       weight: Int): Int {
        return if(comparisonValue.value < value.value) weight else 0
    }

    override fun visit(text: StringSpecificationAttribute, comparisonText: StringSpecificationAttribute,
                       weight: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun visit(texts: StringListSpecificationAttribute, comparisonTexts: StringListSpecificationAttribute,
                       weight: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}