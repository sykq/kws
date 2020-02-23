package io.psc.kws.rest

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

/**
 * RestController for misc things and kotlin experimenting.
 */
@RestController
@RequestMapping("misc")
class MiscRestController {

    private val log = KotlinLogging.logger {}

    /**
     * Generates the given number [iterations] of random integers between 1 and 100 and groups them by number
     * of occurrences into a Map.
     */
    @GetMapping(path = ["clumsyRandoms"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRandomsClumsily(@RequestParam iterations: Int?): Map<String, Any> {
        val randomNumbers = List(iterations ?: 1000) { Random.Default.nextInt(1, 101) }
                .stream()
                .collect({ mutableMapOf<Int, Int>() },
                        { accumulated, current -> accumulated.merge(current, 1) { a, _ -> a + 1 } },
                        { a, b -> a.mapValues { it.value + b[it.key]!! } })

        return mutableMapOf("randoms" to randomNumbers.toSortedMap(),
                "totalIterations" to randomNumbers.values.reduce { a, b -> a + b })
    }

    /**
     * Generates the given number [iterations] of random integers between 1 and 100 and groups them by number
     * of occurrences into a Map.
     */
    @GetMapping(path = ["randomInts"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRandoms(@RequestParam iterations: Int?): Map<String, Any> {
        val randomNumbers = List(iterations ?: 1000) { Random.Default.nextInt(1, 101) }
                .groupingBy { it }
                .aggregate { _, accumulator: Int?, _, first ->
                    if (first) {
                        // return@aggregate not necessary
                        1
                    } else {
                        // return@aggregate not necessary
                        accumulator?.plus(1)
                    }
                }.mapValues { it.value ?: 0 }

        return mutableMapOf("randoms" to randomNumbers.toSortedMap(),
                "totalIterations" to randomNumbers.values.reduce { a, b -> a + b })
    }

    /**
     * Generates the given number [iterations] of random integers between 1 and 100 and groups them by number
     * of occurrences into a Map.
     */
    @GetMapping(path = ["foldRandomInts"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRandomsWithFold(@RequestParam iterations: Int?): Map<String, Any> {
        val randomNumbers = List(iterations ?: 1000) { Random.Default.nextInt(1, 101) }
                .fold(mutableMapOf<Int, Int>(), { acc, i ->
                    acc.merge(i, 1) { a, _ -> a + 1 }
                    return@fold acc
                })

        when (val size = randomNumbers.size) {
            100 -> log.info { "$size" }
            in 1..9 -> log.info { "size smaller than 10 ($size)" }
            else -> log.info { "ok" }
        }

        return mutableMapOf("randoms" to randomNumbers.toSortedMap(),
                "totalIterations" to randomNumbers.values.reduce { a, b -> a + b })
    }
}
