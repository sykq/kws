package io.psc.kws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KwsApplication

fun main(args: Array<String>) {
	runApplication<KwsApplication>(*args)
}
