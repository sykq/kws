package io.psc.configuration

import mu.KotlinLogging
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.properties.Delegates

class CustomConfiguration<T>(init: CustomConfiguration<T>.() -> Unit) {
    val log = KotlinLogging.logger {}

    var startValue = 0
        set(startValue) {
            if (startValue < 0) {
                throw IllegalStateException("startValues less than 0 are not allowed")
            }
            field = startValue
        }
    var name = ""
    var subConfiguration: SubConfiguration = SubConfiguration {}
    val ids = mutableListOf<String>()
    val valueGenerators = mutableListOf<ValueGenerator<Any>>()
    var specificValuePair: Pair<T, *>? = null

    init {
        init.invoke(this)
    }

    fun randomName() {
        name = UUID.randomUUID().toString()
    }

    fun valueGenerator(valueGenerator: ValueGenerator<Any>) {
        valueGenerators.add(valueGenerator)
    }

    fun subConfiguration(init: SubConfiguration.() -> Unit) {
        subConfiguration = subConfiguration.configure(init)
    }

    fun <U> specificValuePair(specificValuePair: Pair<T, U>) {
        val typeFromClass = specificValuePair.first!!::class.java
        val providedType = specificValuePair.second!!::class.java
        log.info { "typeFromClass = $typeFromClass, providedType = $providedType" }
        this.specificValuePair = specificValuePair
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified U> getSpecificValuePairWithReifiedType(): Pair<T, U>? = specificValuePair as Pair<T, U>?

    inline fun configure(crossinline init: CustomConfiguration<T>.() -> Unit): CustomConfiguration<T> {
        init.invoke(this)
        return this
    }

    inline infix operator fun plus(crossinline configure: CustomConfiguration<T>.() -> Unit) {
        this.configure(configure)
    }

    class SubConfiguration(init: SubConfiguration.() -> Unit) {
        private val log = KotlinLogging.logger {}

        val mappings = mutableMapOf<String, Any>()
        var targetDate: ZonedDateTime by Delegates.vetoable(ZonedDateTime.now()) { _, _, newValue ->
            val changeAllowed = newValue.isAfter(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
            if (!changeAllowed) {
                log.info("zonedDateTime is too far in the past")
            }
            return@vetoable changeAllowed
        }

        init {
            init.invoke(this)
        }

        fun configure(init: SubConfiguration.() -> Unit): SubConfiguration {
            init.invoke(this)
            return this
        }
    }
}

fun interface ValueGenerator<T> {

    fun get(): T

}