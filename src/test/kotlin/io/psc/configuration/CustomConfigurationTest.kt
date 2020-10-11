package io.psc.configuration

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime

internal class CustomConfigurationTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun testConfigure() {
        val targetDate = ZonedDateTime.of(2021, 12, 31, 0, 0, 0, 0, ZoneId.of("UTC"))

        val customConfiguration = CustomConfiguration {
            startValue = 55
            randomName()
            subConfiguration {
                mappings["key"] = "value"
                mappings["int"] = 5618
                this.targetDate = targetDate
            }
        }

        log.info { customConfiguration.name }

        assertThat(customConfiguration.name).isNotBlank
        assertThat(customConfiguration.startValue).isEqualTo(55)
        assertThat(customConfiguration.subConfiguration.targetDate).isEqualTo(targetDate)
        assertThat(customConfiguration.subConfiguration.mappings).containsKey("int")

        customConfiguration + { ids.add("1234") }

        assertThat(customConfiguration.ids).contains("1234")

        customConfiguration.configure {
            subConfiguration {
                this.targetDate = ZonedDateTime.of(1999, 12, 7, 13, 38, 42, 908451153, ZoneId.of("UTC"))
            }
        }

        assertThat(customConfiguration.subConfiguration.targetDate).isEqualTo(targetDate)
    }

}
