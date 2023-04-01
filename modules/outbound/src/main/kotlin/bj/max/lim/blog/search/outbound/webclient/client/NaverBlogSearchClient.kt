package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.configuration.NaverBlogSearchProperties
import bj.max.lim.blog.search.outbound.webclient.exception.BlogSearchErrorHandler
import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import bj.max.lim.blog.search.outbound.webclient.request.mapToNaverBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.response.BlogSearchClientResponse
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchClientResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@Order(2)
class NaverBlogSearchClient(
    webClientBuilder: WebClient.Builder,
    naverBlogSearchProperties: NaverBlogSearchProperties,
) : BlogSearchClient {
    private val webClient = webClientBuilder
        .baseUrl(BASE_URL)
        .defaultHeaders {
            it.add(CLIENT_ID_HEADER, naverBlogSearchProperties.clientId)
            it.add(CLIENT_SECRET_HEADER, naverBlogSearchProperties.clientSecret)
        }
        .filter(BlogSearchErrorHandler.errorHandler())
        .build()

    override suspend fun send(request: BlogSearchClientRequest): BlogSearchClientResponse {
        val naverRequest = request.mapToNaverBlogSearchRequest()
        return webClient.get()
            .uri {
                it.path(BLOG_SEARCH_URL)
                    .queryParams(naverRequest.toQueryParams())
                    .build()
            }
            .retrieve()
            .bodyToMono(NaverBlogSearchClientResponse::class.java)
            .awaitSingle()
    }

    companion object {
        val BASE_URL = "https://openapi.naver.com"
        val BLOG_SEARCH_URL = "/v1/search/blog.json"

        val CLIENT_ID_HEADER = "X-Naver-Client-Id"
        val CLIENT_SECRET_HEADER = "X-Naver-Client-Secret"
    }
}
