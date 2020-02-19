package io.psc.kws.rest

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
        val randomNumbers = List(iterations ?: 1000) { Random.Default.nextInt(1, 101) }.groupingBy { it }
                .aggregate { _, accumulator: Int?, _, first ->
                    if (first) {
                        1
                    } else {
                        accumulator?.plus(1)
                    }
                }.mapValues { it.value ?: 0 }

        return mutableMapOf("randoms" to randomNumbers.toSortedMap(),
                "totalIterations" to randomNumbers.values.reduce { a, b -> a + b })
    }
}
