package bj.max.lim.blog.search.domain.service

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
    private val blogSearchRepository: BlogSearchRepository,
    private val keywordRankRepository: KeywordRankRepository,
) {
    suspend fun blogSearch(blogSearchContext: BlogSearchContext): BlogSearch {
        keywordRankRepository.incrementCountByKeyword(blogSearchContext.query)
        return blogSearchRepository.findByBlogSearchContext(blogSearchContext)
    }
}
