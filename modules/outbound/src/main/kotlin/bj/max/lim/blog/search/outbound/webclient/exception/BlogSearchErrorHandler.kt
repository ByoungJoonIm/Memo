package bj.max.lim.blog.search.outbound.webclient.exception

import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import reactor.core.publisher.Mono

/**
 * 4xx -> IllegarArgumentException
 * 5xx -> IllegarStateException
 * */
class BlogSearchErrorHandler {
    companion object {
        fun errorHandler(): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
                return@ofResponseProcessor if (clientResponse.statusCode().is4xxClientError) {
                    clientResponse.bodyToMono(String::class.java)
                        .flatMap { errorBody -> Mono.error(IllegalArgumentException(errorBody)) }
                } else if (clientResponse.statusCode().is5xxServerError) {
                    clientResponse.bodyToMono(String::class.java)
                        .flatMap { errorBody -> Mono.error(IllegalStateException(errorBody)) }
                } else {
                    Mono.just(clientResponse)
                }
            }
        }
    }
}
