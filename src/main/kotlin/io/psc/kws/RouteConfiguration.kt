package io.psc.kws

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RouteConfiguration {

    @Bean
    fun routes() = router {
        GET("/kws").nest {
            GET("/echo") {
                ServerResponse.ok().bodyValue(it.queryParam("text"))
            }
            GET("/transform") {
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mapOf("text" to it.queryParam("text").map { s -> s.toUpperCase() }))
            }
        }
    }

}