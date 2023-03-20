package bj.max.lim.blog.search.server.controller

import bj.max.lim.blog.search.domain.service.BlogSearchService
import bj.max.lim.blog.search.domain.service.KeywordRankService
import bj.max.lim.blog.search.server.request.BlogSearchRequest
import bj.max.lim.blog.search.server.request.mapToBlogSearchContext
import bj.max.lim.blog.search.server.response.BlogSearchResponse
import bj.max.lim.blog.search.server.response.KeywordRankResponse
import bj.max.lim.blog.search.server.response.mapToBlogSearchResponse
import bj.max.lim.blog.search.server.response.mapToKeywordRankResponse
import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class BlogSearchController(
    private val blogSearchService: BlogSearchService,
    private val keywordRankService: KeywordRankService,
) {

    @GetMapping("/BlogSearch")
    fun blogSearch(
        @RequestParam query: String,
        @RequestParam(defaultValue = "1") page: String,
        @RequestParam(defaultValue = "10") pageSize: String,
        @RequestParam(defaultValue = "accuracy") sorting: String,
    ): Mono<BlogSearchResponse> {
        return mono {
            val blogSearchRequest = BlogSearchRequest(
                query = query,
                page = page.toInt(),
                pageSize = pageSize.toInt(),
                blogSearchSortingOption = BlogSearchRequest.BlogSearchSortingOption.fromAlias(sorting),
            )
            blogSearchService.blogSearch(blogSearchRequest.mapToBlogSearchContext()).mapToBlogSearchResponse()
        }
    }

    @GetMapping("/KeywordRank")
    fun keywordRank(): Mono<KeywordRankResponse> {
        return mono {
            keywordRankService.findTop10Keywords().mapToKeywordRankResponse()
        }
    }
}
