package io.psc.hs

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.io.File

internal class LongestWordTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun testFindLongestWord() {
        log.info(File("src/test/resources/words_sequence.txt").readText()
                .split(" ", "\n", "\r")
                .map { it.length }
                .maxOrNull()!!.toString())
    }

    @Test
    fun testFindLongestWordWithLinesAsInput() {
        log.info(File("src/test/resources/words_sequence.txt").readLines()
                .map { it.length }
                .maxOrNull()!!.toString())
    }
}