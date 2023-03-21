package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.aggregate.impl.KakaoBlogSearchImpl
import bj.max.lim.blog.search.domain.repository.BlogSearchClientRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.domain.service.iface.mapToKakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.client.KakaoBlogSearchClient
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class KakaoBlogSearchClientRepositoryImpl(
    private val kakaoBlogSearchClient: KakaoBlogSearchClient,
) : BlogSearchClientRepository {

    override suspend fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch {
        val kakaoBlogSearchResponse = kakaoBlogSearchClient.send(blogSearchContext.mapToKakaoBlogSearchRequest())
            .awaitSingle()
        return KakaoBlogSearchImpl(kakaoBlogSearchResponse)
    }
}
