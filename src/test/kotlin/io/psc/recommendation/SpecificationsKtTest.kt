package io.psc.recommendation

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SpecificationsKtTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun filter() {
        val result1 = filter(requestedSpecification1)
        log.info { "first: $result1" }
        Assertions.assertThat(result1).isEqualTo(1.0)

        val result2 = filter(requestedSpecification2)
        log.info { "first: $result2" }
        Assertions.assertThat(result2).isLessThan(1.0)

        val result3 = filter(requestedSpecification3)
        log.info { "first: $result3" }
        Assertions.assertThat(result3).isEqualTo(1.0)
    }
}