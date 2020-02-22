package io.psc.recommendation2

import java.math.BigDecimal
import java.time.Period


abstract class SpecificationAttribute<T>(val name: String, val value: T){
}

//abstract class SpecificationAttribute(val name: String, val value: Any){
//    abstract fun accept(evaluationVisitor: EvaluationVisitor): Int
//}

class PeriodSpecificationAttribute(name: String, value: Period): SpecificationAttribute<Period>(name, value)

class BigDecimalSpecificationAttribute(name: String, value: BigDecimal): SpecificationAttribute<BigDecimal>(name, value)

class StringListSpecificationAttribute(name: String, value: List<String>): SpecificationAttribute<List<String>>(name, value)

class StringSpecificationAttribute(name: String, value: String): SpecificationAttribute<String>(name, value)

data class RequestedSpecificationAttribute<T>(val attribute: T,
                                              val evaluationFunction: EvaluationFunction<T>)