package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.outbound.webclient.client.KakaoBlogSearchClient
import bj.max.lim.blog.search.outbound.webclient.client.NaverBlogSearchClient
import org.springframework.stereotype.Repository

@Repository
class BlogSearchRepositoryImpl(
    private val kakaoBlogSearchClient: KakaoBlogSearchClient,
    private val naverBlogSearchClient: NaverBlogSearchClient,
) : BlogSearchRepository {
    override fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch {
        TODO("Not yet implemented")
    }
}
