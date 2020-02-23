package io.psc.recommendation3

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class SpecificationsKtTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun filter() {
        val result1 = filter(requestedSpecification1)
        log.info { "first: $result1" }
        Assertions.assertThat(result1).isEqualTo(1.0)

        val result2 = filter(requestedSpecification2)
        log.info { "second: $result2" }
        Assertions.assertThat(result2).isLessThan(1.0)

        val result3 = filter(requestedSpecification3)
        log.info { "third: $result3" }
        Assertions.assertThat(result3).isEqualTo(1.0)

        val result4 = filter(requestedSpecification4)
        log.info { "fourth: $result4" }
        Assertions.assertThat(result4).isEqualTo(1.0)

        val result5 = filter(requestedSpecification5)
        log.info { "fifth: $result5" }
        Assertions.assertThat(result5).isEqualTo(1.0)

        val result6 = filter(requestedSpecification6)
        log.info { "sixth: $result6" }
        Assertions.assertThat(result6).isLessThan(1.0)

    }
}