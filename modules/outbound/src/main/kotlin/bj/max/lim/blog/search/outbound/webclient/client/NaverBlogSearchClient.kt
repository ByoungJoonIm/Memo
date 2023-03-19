package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.configuration.NaverBlogSearchProperties
import bj.max.lim.blog.search.outbound.webclient.exception.BlogSearchErrorHandler
import bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class NaverBlogSearchClient(
    webClientBuilder: WebClient.Builder,
    naverBlogSearchProperties: NaverBlogSearchProperties,
) {
    private val webClient = webClientBuilder
        .baseUrl(BASE_URL)
        .defaultHeaders {
            it.add(CLIENT_ID_HEADER, naverBlogSearchProperties.clientId)
            it.add(CLIENT_SECRET_HEADER, naverBlogSearchProperties.clientSecret)
        }
        .filter(BlogSearchErrorHandler.errorHandler())
        .build()

    suspend fun send(request: NaverBlogSearchRequest): Mono<NaverBlogSearchResponse> {
        return webClient.get()
            .uri {
                it.path(BLOG_SEARCH_URL)
                    .queryParams(request.toQueryParams())
                    .build()
            }
            .retrieve()
            .bodyToMono(NaverBlogSearchResponse::class.java)
    }

    companion object {
        val BASE_URL = "https://openapi.naver.com"
        val BLOG_SEARCH_URL = "/v1/search/blog.json"

        val CLIENT_ID_HEADER = "X-Naver-Client-Id"
        val CLIENT_SECRET_HEADER = "X-Naver-Client-Secret"
    }
}
