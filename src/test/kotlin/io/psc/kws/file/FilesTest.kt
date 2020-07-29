package io.psc.kws.file

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.io.File

internal class FilesTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun testCountNumbers() {
        val count = File("src/test/resources/numbers.txt").readLines().count { it.toIntOrNull() != null}
        log.info(count.toString())
    }

    @Test
    fun testCountWords() {
        val count = File("src/test/resources/text.txt").readText().split(" ").count()
        log.info(count.toString())
    }
}