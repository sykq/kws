package io.psc.configuration

import mu.KotlinLogging
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.properties.Delegates

class CustomConfiguration(init: CustomConfiguration.() -> Unit) {
    var startValue = 0
        set(startValue) {
            if (startValue < 0) {
                throw IllegalStateException("startValues less than 0 are not allowed")
            }
            field = startValue
        }
    var name = ""
    var subConfiguration: SubConfiguration = SubConfiguration{}
    val ids = mutableListOf<String>()

    init {
        init.invoke(this)
    }

    fun randomName() {
        name = UUID.randomUUID().toString()
    }

    fun subConfiguration(init: SubConfiguration.() -> Unit) {
        subConfiguration = subConfiguration.configure(init)
    }

    fun configure(init: CustomConfiguration.() -> Unit): CustomConfiguration {
        init.invoke(this)
        return this
    }

    infix operator fun plus(configure: CustomConfiguration.() -> Unit) {
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