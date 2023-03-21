package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.common.exception.AllClientUnavailableException
import bj.max.lim.blog.search.common.exception.Client5XXException
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.repository.BlogSearchClientRepository
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class BlogSearchRepositoryImpl(
    private val blogSearchClientRepositoryList: List<BlogSearchClientRepository>,
) : BlogSearchRepository {

    private val logger = LoggerFactory.getLogger(BlogSearchRepositoryImpl::class.java)

    override suspend fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch {
        blogSearchClientRepositoryList.map {
            try {
                return it.findByBlogSearchContext(blogSearchContext)
            } catch (e: Client5XXException) {
                // 5XX 예외는 외부 서버가 잘못된 경우이므로 info로 남긴다.
                logger.info(e.stackTraceToString())
            } catch (e: Exception) {
                // 4xx 예외 및 다른 예외 발생은 spec이 잘못되었을 수 있으니 warn으로 남긴다.
                logger.warn(e.stackTraceToString())
            }
        }

        throw AllClientUnavailableException()
    }
}
