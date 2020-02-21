package io.psc.recommendation

import java.math.BigDecimal
import java.time.Period

data class DefaultSpecification(val period: SpecificationAttribute<Period>,
                                val price: SpecificationAttribute<BigDecimal>,
                                val allowedNames: SpecificationAttribute<List<String>>)

val DEFAULT_SPECIFICATION = DefaultSpecification(
        SpecificationAttribute("period", Period.ofMonths(2)),
        SpecificationAttribute("price", BigDecimal.valueOf(2999.90)),
        SpecificationAttribute("allowedNames", listOf("def", "ghi")))

val requestedSpecification1: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification2: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute<List<String>>(
                        SpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<List<String>> {
                            override fun apply(inputAttribute: SpecificationAttribute<List<String>>,
                                               targetAttribute: SpecificationAttribute<List<String>>): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isNotEmpty())
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification3: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute<List<String>>(
                        SpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<List<String>> {
                            override fun apply(inputAttribute: SpecificationAttribute<List<String>>,
                                               targetAttribute: SpecificationAttribute<List<String>>): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isEmpty())
                                    weight
                                else 0
                            }
                        })
        )


fun filter(requirements: List<RequestedSpecificationAttribute<*>>): Double {

    val requirementsByName = requirements.associateBy { it.attribute.name }

    val weight1 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.period)
    val weight2 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.price)
    val weight3 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.allowedNames)

    return (weight1 + weight2 + weight3) / 300.0
}

private fun <T> evaluate(requirements: Map<String, RequestedSpecificationAttribute<*>>,
                         attribute: SpecificationAttribute<T>): Int {
    @Suppress("UNCHECKED_CAST")
    val requestedSpecificationAttribute = requirements[attribute.name] as? RequestedSpecificationAttribute<T>

    return requestedSpecificationAttribute.let {
        it?.evaluationFunction?.apply(it.attribute, attribute)
    } ?: EvaluationFunction.weight
}