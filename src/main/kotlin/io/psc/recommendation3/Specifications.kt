package io.psc.recommendation3

import io.psc.recommendation3.evaluation.AroundEvaluationOptions
import io.psc.recommendation3.evaluation.AroundEvaluationVisitor
import io.psc.recommendation3.evaluation.LessThanEvaluationVisitor
import java.math.BigDecimal
import java.time.Period

data class DefaultSpecification(val period: SpecificationAttribute<Period>,
                                val price: SpecificationAttribute<BigDecimal>,
                                val allowedNames: SpecificationAttribute<List<String>>)

val DEFAULT_SPECIFICATION = DefaultSpecification(
        PeriodSpecificationAttribute("period", Period.ofMonths(2)),
        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(2999.90)),
        StringListSpecificationAttribute("allowedNames", listOf("def", "ghi")))

val requestedSpecification1: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute, Nothing> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute, Nothing> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification2: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute, Nothing> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute, Nothing> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute(
                        StringListSpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<StringListSpecificationAttribute, Nothing> {
                            override fun apply(inputAttribute: StringListSpecificationAttribute,
                                               targetAttribute: StringListSpecificationAttribute): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isNotEmpty())
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification3: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                object : EvaluationFunction<PeriodSpecificationAttribute, Nothing> {
                    override fun apply(inputAttribute: PeriodSpecificationAttribute,
                                       targetAttribute: PeriodSpecificationAttribute): Int {
                        return if (!targetAttribute.value.minus(inputAttribute.value).isNegative)
                            weight
                        else 0
                    }
                }),
                RequestedSpecificationAttribute(
                        BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(3000.00)),
                        object : EvaluationFunction<BigDecimalSpecificationAttribute, Nothing> {
                            override fun apply(inputAttribute: BigDecimalSpecificationAttribute,
                                               targetAttribute: BigDecimalSpecificationAttribute): Int {
                                return if (targetAttribute.value <= inputAttribute.value)
                                    weight
                                else 0
                            }
                        }),
                RequestedSpecificationAttribute(
                        StringListSpecificationAttribute("allowedNames", listOf("abc")),
                        object : EvaluationFunction<StringListSpecificationAttribute, Nothing> {
                            override fun apply(inputAttribute: StringListSpecificationAttribute,
                                               targetAttribute: StringListSpecificationAttribute): Int {
                                return if (targetAttribute.value.intersect(inputAttribute.value).isEmpty())
                                    weight
                                else 0
                            }
                        })
        )

val requestedSpecification4: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(3)),
                PeriodEvaluationFunction(
                        LessThanEvaluationVisitor<Nothing>())
        ), RequestedSpecificationAttribute(
                BigDecimalSpecificationAttribute("price", BigDecimal.valueOf(2999.99)),
                BigDecimalEvaluationFunction(
                        LessThanEvaluationVisitor<Nothing>())))

val requestedSpecification5: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(2)),
                PeriodEvaluationFunction(
                        AroundEvaluationVisitor<Period>(),
                        AroundEvaluationOptions(Period.ofMonths(1), Period.ofMonths(1)))
        ))

val requestedSpecification6: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>> =
        listOf(RequestedSpecificationAttribute(
                PeriodSpecificationAttribute("period", Period.ofMonths(3)),
                PeriodEvaluationFunction(
                        AroundEvaluationVisitor<Period>(),
                        AroundEvaluationOptions(Period.ofMonths(1), Period.ofMonths(1)))
        ))


fun filter(requirements: List<RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>>): Double {

    val requirementsByName = requirements.associateBy { it.attribute.name }

    val weight1 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.period)
    val weight2 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.price)
    val weight3 = evaluate(requirementsByName, DEFAULT_SPECIFICATION.allowedNames)

    return (weight1 + weight2 + weight3) / requirements.fold(0.0,
            { sum, specificationAttribute -> sum + specificationAttribute.evaluationFunction.weight })
}

private fun <T> evaluate(
        requirements: Map<String, RequestedSpecificationAttribute<out SpecificationAttribute<*>, *>>,
        attribute: SpecificationAttribute<T>): Int {
    @Suppress("UNCHECKED_CAST")
    val requestedSpecificationAttribute = requirements[attribute.name] as? RequestedSpecificationAttribute<SpecificationAttribute<T>, *>

    return requestedSpecificationAttribute.let {
        it?.evaluationFunction?.apply(it.attribute, attribute)
    } ?: 0
}