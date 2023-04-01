package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.configuration.KakaoBlogSearchProperties
import bj.max.lim.blog.search.outbound.webclient.exception.BlogSearchErrorHandler
import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import bj.max.lim.blog.search.outbound.webclient.request.mapToKakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.response.BlogSearchClientResponse
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchClientResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@Order(1)
class KakaoBlogSearchClient(
    webClientBuilder: WebClient.Builder,
    kakaoBlogSearchProperties: KakaoBlogSearchProperties,
) : BlogSearchClient {
    private val webClient = webClientBuilder
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK ${kakaoBlogSearchProperties.apiKey}")
        .filter(BlogSearchErrorHandler.errorHandler())
        .build()

    override suspend fun send(request: BlogSearchClientRequest): BlogSearchClientResponse {
        val kakaoRequest = request.mapToKakaoBlogSearchRequest()
        return webClient.get()
            .uri {
                it.path(BLOG_SEARCH_URL)
                    .queryParams(kakaoRequest.toQueryParams())
                    .build()
            }
            .retrieve()
            .bodyToMono(KakaoBlogSearchClientResponse::class.java)
            .awaitSingle()
    }

    companion object {
        val BASE_URL = "https://dapi.kakao.com/v2"
        val BLOG_SEARCH_URL = "/search/blog"
    }
}
