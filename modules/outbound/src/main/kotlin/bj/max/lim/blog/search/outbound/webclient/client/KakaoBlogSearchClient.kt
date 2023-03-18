package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.request.KakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class KakaoBlogSearchClient(
    webClientBuilder: WebClient.Builder,
) {
    private val webClient = webClientBuilder
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK $API_KEY")
        .build()

    suspend fun send(request: KakaoBlogSearchRequest): Mono<KakaoBlogSearchResponse> {
        return webClient.get()
            .uri {
                it.path(BLOG_SEARCH_URL)
                    .queryParams(request.toQueryParams())
                    .build()
            }
            .retrieve()
            .bodyToMono(KakaoBlogSearchResponse::class.java)
    }

    companion object {
        val BASE_URL = "https://dapi.kakao.com/v2"
        val BLOG_SEARCH_URL = "/search/blog"

        // will be changed to configuration
        val API_KEY = "3fe1d1cf3d8a9924be09220c78cd6280"
    }
}
