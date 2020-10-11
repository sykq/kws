package io.psc.configuration

import java.util.*

class CustomConfiguration {
    var startValue = 0
        set(startValue) {
            if (startValue < 0) {
                throw IllegalStateException("startValues less than 0 are not allowed")
            }
            field = startValue
        }
    var name = ""
    val ids = mutableListOf<String>()

    fun randomName(){
        name = UUID.randomUUID().toString()
    }

    fun configure(init: CustomConfiguration.() -> Unit): CustomConfiguration {
        init.invoke(this)
        return this
    }

    infix operator fun plus(configure: CustomConfiguration.() -> Unit) {
        this.configure(configure)
    }
}