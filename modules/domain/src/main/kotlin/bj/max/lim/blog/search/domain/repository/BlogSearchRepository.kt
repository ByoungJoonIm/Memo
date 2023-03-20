package bj.max.lim.blog.search.domain.repository

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext

interface BlogSearchRepository {
    suspend fun findByBlogSearchContext(blogSearchContext: BlogSearchContext): BlogSearch
}
