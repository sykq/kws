package io.psc.recommendation

import java.math.BigDecimal
import java.time.Period

data class DefaultSpecification(val period: SpecificationAttribute<Period>,
                                val price: SpecificationAttribute<BigDecimal>,
                                val allowedNames: SpecificationAttribute<List<String>>) {
}

val DEFAULT_SPECIFICATION = DefaultSpecification(
        SpecificationAttribute("period", Period.ofMonths(2), 1),
        SpecificationAttribute("price", BigDecimal.valueOf(2999.90), 1),
        SpecificationAttribute("allowedNames", listOf("def", "ghi"), 1))

val requestedSpecification1: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2), 1),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            inputAttribute.weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00), 1),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    inputAttribute.weight
                                else 0
                            }
                        })
        )

val requestedSpecification2: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2), 1),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            inputAttribute.weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00), 1),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    inputAttribute.weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute<List<String>>(
                        SpecificationAttribute("allowedNames", listOf("abc"), 1),
                        object : EvaluationFunction<List<String>> {
                            override fun apply(inputAttribute: SpecificationAttribute<List<String>>,
                                               targetAttribute: SpecificationAttribute<List<String>>): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isNotEmpty())
                                    inputAttribute.weight
                                else 0
                            }
                        })
        )

val requestedSpecification3: List<RequestedSpecificationAttribute<*>> =
        listOf(RequestedSpecificationAttribute<Period>(
                SpecificationAttribute("period", Period.ofMonths(2), 1),
                object : EvaluationFunction<Period> {
                    override fun apply(inputAttribute: SpecificationAttribute<Period>,
                                       targetAttribute: SpecificationAttribute<Period>): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            inputAttribute.weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute<BigDecimal>(
                        SpecificationAttribute("price", BigDecimal.valueOf(3000.00), 1),
                        object : EvaluationFunction<BigDecimal> {
                            override fun apply(inputAttribute: SpecificationAttribute<BigDecimal>,
                                               targetAttribute: SpecificationAttribute<BigDecimal>): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    inputAttribute.weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute<List<String>>(
                        SpecificationAttribute("allowedNames", listOf("abc"), 1),
                        object : EvaluationFunction<List<String>> {
                            override fun apply(inputAttribute: SpecificationAttribute<List<String>>,
                                               targetAttribute: SpecificationAttribute<List<String>>): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isEmpty())
                                    inputAttribute.weight
                                else 0
                            }
                        })
        )


fun filter(requirements: List<RequestedSpecificationAttribute<*>>): Int {

    val requirementsByName = requirements.associateBy { it.attribute.name }

    @Suppress("UNCHECKED_CAST")
    val weight1 = (requirementsByName[DEFAULT_SPECIFICATION.period.name] as? RequestedSpecificationAttribute<Period>).let {
        it?.evaluationFunction?.apply(it.attribute, DEFAULT_SPECIFICATION.period)
    } ?: 1

    @Suppress("UNCHECKED_CAST")
    val weight2 = (requirementsByName[DEFAULT_SPECIFICATION.price.name] as? RequestedSpecificationAttribute<BigDecimal>).let {
        it?.evaluationFunction?.apply(it.attribute, DEFAULT_SPECIFICATION.price)
    } ?: 1

    @Suppress("UNCHECKED_CAST")
    val weight3 = (requirementsByName[DEFAULT_SPECIFICATION.allowedNames.name] as? RequestedSpecificationAttribute<List<String>>).let {
        it?.evaluationFunction?.apply(it.attribute, DEFAULT_SPECIFICATION.allowedNames)
    } ?: 1

    return (weight1 + weight2 + weight3) / 3
}