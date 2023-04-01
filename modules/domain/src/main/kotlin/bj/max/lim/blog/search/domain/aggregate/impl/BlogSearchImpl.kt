package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.outbound.webclient.response.BlogSearchClientResponse

class BlogSearchImpl(
    private val blogSearchResponse: BlogSearchClientResponse,
) : BlogSearch {
    override val total: Int
        get() = blogSearchResponse.total
    override val blogList: List<Blog>
        get() = blogSearchResponse.blogList.map { BlogImpl(it) }
}
