package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.aggregate.impl.KakaoBlogSearchImpl
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.domain.service.iface.mapToKakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.client.KakaoBlogSearchClient
import bj.max.lim.blog.search.outbound.webclient.client.NaverBlogSearchClient
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class BlogSearchRepositoryImpl(
    private val kakaoBlogSearchClient: KakaoBlogSearchClient,
    private val naverBlogSearchClient: NaverBlogSearchClient,
) : BlogSearchRepository {

    /**
     * TODO: kakao 장애시 naver 요청으로 처리될 수 있도록 수정
     * TODO: test 작성
     * */
    override suspend fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch {
        val kakaoBlogSEarchResponse = kakaoBlogSearchClient.send(blogSearchContext.mapToKakaoBlogSearchRequest())
            .awaitSingle()
        return KakaoBlogSearchImpl(kakaoBlogSEarchResponse)
    }
}
