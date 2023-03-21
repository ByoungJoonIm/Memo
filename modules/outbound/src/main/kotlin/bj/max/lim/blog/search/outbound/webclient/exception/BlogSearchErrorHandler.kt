package bj.max.lim.blog.search.outbound.webclient.exception

import bj.max.lim.blog.search.common.exception.Client4XXException
import bj.max.lim.blog.search.common.exception.Client5XXException
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
                        .flatMap { errorBody -> Mono.error(Client4XXException(errorBody)) }
                } else if (clientResponse.statusCode().is5xxServerError) {
                    clientResponse.bodyToMono(String::class.java)
                        .flatMap { errorBody -> Mono.error(Client5XXException(errorBody)) }
                } else {
                    Mono.just(clientResponse)
                }
            }
        }
    }
}
