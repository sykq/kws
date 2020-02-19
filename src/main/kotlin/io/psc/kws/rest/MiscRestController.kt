package io.psc.kws.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("misc")
class MiscRestController {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMisc(): Map<String, Any> {
        val randomNumbers = List(1000) { Random.Default.nextInt(1, 101) }
                .stream()
                .collect({ mutableMapOf<Int, Int>() },
                        { accumulated, current -> accumulated.merge(current, 1) { a, _ -> a + 1 } },
                        { a, b -> a.mapValues { it.value + b[it.key]!! } })

        return mutableMapOf("randoms" to randomNumbers.toSortedMap())
    }
}
