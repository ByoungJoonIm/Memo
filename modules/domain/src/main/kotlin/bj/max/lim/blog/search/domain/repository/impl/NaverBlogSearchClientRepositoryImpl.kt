package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.aggregate.impl.NaverBlogSearchImpl
import bj.max.lim.blog.search.domain.repository.BlogSearchClientRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.domain.service.iface.mapToNaverBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.client.NaverBlogSearchClient
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class NaverBlogSearchClientRepositoryImpl(
    private val naverBlogSearchClient: NaverBlogSearchClient,
) : BlogSearchClientRepository {

    override suspend fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch {
        val naverBlogSearchResponse = naverBlogSearchClient.send(blogSearchContext.mapToNaverBlogSearchRequest())
            .awaitSingle()
        return NaverBlogSearchImpl(naverBlogSearchResponse)
    }
}
