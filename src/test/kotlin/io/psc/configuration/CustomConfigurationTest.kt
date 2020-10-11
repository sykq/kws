package io.psc.configuration

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CustomConfigurationTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun testConfigure() {
        val customConfiguration = CustomConfiguration()
                .configure {
                    startValue = 55
                    randomName()
                }

        log.info { customConfiguration.name }

        assertThat(customConfiguration.name).isNotBlank()
        assertThat(customConfiguration.startValue).isEqualTo(55)

        customConfiguration + { ids.add("1234")}

        assertThat(customConfiguration.ids).contains("1234")
    }

}
