package io.psc.recommendation2

import java.io.Serializable
import java.math.BigDecimal
import java.time.Period

data class DefaultSpecification(val period: SpecificationAttribute<Period>,
                                val price: SpecificationAttribute<BigDecimal>,
                                val allowedNames: SpecificationAttribute<List<String>>)

val DEFAULT_SPECIFICATION = DefaultSpecification(
        PeriodSpecificationAttribute("period", Period.ofMonths(2)),
        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(2999.90)),
        StringListSpecificationAttribute("allowedNames", listOf("def", "ghi")))

val requestedSpecification1: List<RequestedSpecificationAttribute<out SpecificationAttribute<out Any>>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }

                    override var evaluationVisitor: EvaluationVisitor?
                        get() = TODO(
                                "not implemented") //To change initializer of created properties use File | Settings | File Templates.
                        set(value) {}
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }

                            override var evaluationVisitor: EvaluationVisitor?
                                get() = TODO(
                                        "not implemented") //To change initializer of created properties use File | Settings | File Templates.
                                set(value) {}
                        })
        )

val requestedSpecification2: List<RequestedSpecificationAttribute<out SpecificationAttribute<out Any>>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute(
                        StringListSpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<StringListSpecificationAttribute> {
                            override fun apply(inputAttribute: StringListSpecificationAttribute,
                                               targetAttribute: StringListSpecificationAttribute): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isNotEmpty())
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification3: List<RequestedSpecificationAttribute<out SpecificationAttribute<out Any>>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute(
                        StringListSpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<StringListSpecificationAttribute> {
                            override fun apply(inputAttribute: StringListSpecificationAttribute,
                                               targetAttribute: StringListSpecificationAttribute): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isEmpty())
                                    weight
                                else 0
                            }
                        })
        )


fun filter(requirements: List<RequestedSpecificationAttribute<out SpecificationAttribute<out Any>>>): Double {

    val requirementsByName = requirements.associateBy { it.attribute.name }

    val weight1 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.period)
    val weight2 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.price)
    val weight3 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.allowedNames)

    return (weight1 + weight2 + weight3) / requirements.fold(0.0,
            { sum, specificationAttribute -> sum + specificationAttribute.evaluationFunction.weight })
}

private fun <T> evaluate(requirements: Map<String, RequestedSpecificationAttribute<out SpecificationAttribute<out Any>>>,
                         attribute: SpecificationAttribute<T>): Int {
    @Suppress("UNCHECKED_CAST")
    val requestedSpecificationAttribute = requirements[attribute.name] as? RequestedSpecificationAttribute<SpecificationAttribute<T>>

    return requestedSpecificationAttribute.let {
        it?.evaluationFunction?.apply(it.attribute, attribute)
    } ?: 0
}