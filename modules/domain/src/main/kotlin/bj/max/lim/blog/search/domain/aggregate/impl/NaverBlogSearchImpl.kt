package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchResponse

class NaverBlogSearchImpl(
    private val naverBlogSearchResponse: NaverBlogSearchResponse,
) : BlogSearch {
    override val total: Int
        get() = naverBlogSearchResponse.total
    override val blogList: List<Blog>
        get() = naverBlogSearchResponse.items.map { NaverBlogImpl(it) }
}
