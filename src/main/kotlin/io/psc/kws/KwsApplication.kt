package io.psc.kws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.support.beans
import org.springframework.web.server.WebFilter

@SpringBootApplication
class KwsApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(KwsApplication::class.java)
            .initializers(
                    beans {
                        bean {
                            val builder = ref<RouteLocatorBuilder>()
                            builder.routes().route("tw_redirect") {
                                        it.path("/tw").uri("https://www.twitch.tv/")
                                    }
                                    .build()
                        }
                        bean {
                            // WebFilter to add the server context-path to a request's url ("redirect")
                            val contextPath = ref<ServerProperties>().servlet.contextPath
                            WebFilter { exchange, chain ->
                                val request = exchange.request
                                if (request.uri.path.startsWith(contextPath)) {
                                    chain.filter(
                                            exchange.mutate()
                                                    .request(
                                                            request.mutate()
                                                                    .contextPath(contextPath)
                                                                    .build())
                                                    .build())
                                } else {
                                    chain.filter(exchange)
                                }
                            }
                        }
                    }
            )
            .run(*args)
//    runApplication<KwsApplication>(*args)
}