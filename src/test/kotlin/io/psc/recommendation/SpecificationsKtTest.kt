package io.psc.recommendation

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SpecificationsKtTest {

    @Test
    fun filter() {
        val result1 = filter(requestedSpecification1)
        Assertions.assertThat(result1).isEqualTo(1)

        val result2 = filter(requestedSpecification2)
        Assertions.assertThat(result2).isEqualTo(0)

        val result3 = filter(requestedSpecification3)
        Assertions.assertThat(result3).isEqualTo(1)
    }
}