package io.psc.recommendation

data class SpecificationAttribute<T>(val name: String, val value: T)

data class RequestedSpecificationAttribute<T>(val attribute: SpecificationAttribute<T>,
                                              val evaluationFunction: EvaluationFunction<T>)