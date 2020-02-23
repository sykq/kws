package io.psc.recommendation3

import java.math.BigDecimal
import java.time.Period


abstract class SpecificationAttribute<T>(val name: String, val value: T)

class PeriodSpecificationAttribute(name: String, value: Period) : SpecificationAttribute<Period>(name, value)

class BigDecimalSpecificationAttribute(name: String, value: BigDecimal) : SpecificationAttribute<BigDecimal>(name,
        value)

class StringListSpecificationAttribute(name: String, value: List<String>) : SpecificationAttribute<List<String>>(name,
        value)

class StringSpecificationAttribute(name: String, value: String) : SpecificationAttribute<String>(name, value)

data class RequestedSpecificationAttribute<T : SpecificationAttribute<*>, O>(val attribute: T,
                                                                             val evaluationFunction: EvaluationFunction<T, O>)