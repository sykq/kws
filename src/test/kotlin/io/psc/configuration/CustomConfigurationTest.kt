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

        val customConfiguration = CustomConfiguration<String> {
            startValue = 55
            randomName()
            subConfiguration {
                mappings["key"] = "value"
                mappings["int"] = 5618
                this.targetDate = targetDate
            }
            valueGenerator { "Hello!" }
            specificValuePair("Test" to 11)
        }

        log.info { customConfiguration.name }

        assertThat(customConfiguration.name).isNotBlank
        assertThat(customConfiguration.startValue).isEqualTo(55)
        assertThat(customConfiguration.subConfiguration.targetDate).isEqualTo(targetDate)
        assertThat(customConfiguration.subConfiguration.mappings).containsKey("int")
        assertThat(customConfiguration.valueGenerators).size().isEqualTo(1)
        assertThat(customConfiguration.specificValuePair).isEqualTo("Test" to 11)

        val castedSpecificValuePair = customConfiguration.getSpecificValuePairWithReifiedType<Pair<String, Int>>()
        assertThat(castedSpecificValuePair).isEqualTo("Test" to 11)

        customConfiguration + { ids.add("1234") }

        assertThat(customConfiguration.ids).contains("1234")

        customConfiguration.configure {
            subConfiguration {
                this.targetDate = ZonedDateTime.of(1999, 12, 7, 13, 38, 42, 908451153, ZoneId.of("UTC"))
            }
        }

        assertThat(customConfiguration.subConfiguration.targetDate).isEqualTo(targetDate)
    }

    @Test
    fun testConfigureWithIntegerGeneric() {

        val customConfiguration = CustomConfiguration<Int> {
            startValue = 4
            randomName()
            valueGenerator { 777 }
            valueGenerator { "abc" }
            specificValuePair(1234 to "xyz")
            ids.add("1234")
        }

        log.info { customConfiguration.name }

        assertThat(customConfiguration.name).isNotBlank
        assertThat(customConfiguration.startValue).isEqualTo(4)
        assertThat(customConfiguration.valueGenerators).size().isEqualTo(2)
        assertThat(customConfiguration.specificValuePair).isEqualTo(1234 to "xyz")

        customConfiguration + { ids.add("4321") }

        assertThat(customConfiguration.ids).contains("1234", "4321")

    }

}
