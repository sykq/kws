package io.psc.ksw.utils

import io.psc.kws.utils.trimToLowerCase
import io.psc.kws.utils.trimToUpperCase
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MiscUtilsTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun testTrimToLowerCase() {
        var input: String? = "   aBcDefgH   "
        var expected = "abcdefgh"
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToLowerCase().apply(input)).isEqualTo(expected)
        assertThat(trimToLowerCase(input)).isEqualTo(expected)

        input = "     "
        expected = ""
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToLowerCase().apply(input)).isEqualTo(expected)
        assertThat(trimToLowerCase(input)).isEqualTo(expected)

        input = null
        expected = ""
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToLowerCase().apply(input)).isEqualTo(expected)
        assertThat(trimToLowerCase(input)).isEqualTo(expected)
    }

    @Test
    fun testTrimToUpperCase() {
        var input: String? = "   aBcDefgH   "
        var expected = "ABCDEFGH"
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToUpperCase().apply(input)).isEqualTo(expected)
        assertThat(trimToUpperCase(input)).isEqualTo(expected)

        input = "     "
        expected = ""
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToUpperCase().apply(input)).isEqualTo(expected)
        assertThat(trimToUpperCase(input)).isEqualTo(expected)

        input = null
        expected = ""
        log.info { "input: $input -> expected $expected" }
        assertThat(trimToUpperCase().apply(input)).isEqualTo(expected)
        assertThat(trimToUpperCase(input)).isEqualTo(expected)

    }

}