package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchResponse

class KakaoBlogSearchImpl(
    private val kakaoBlogSearchResponse: KakaoBlogSearchResponse,
) : BlogSearch {
    override val total: Int
        get() = kakaoBlogSearchResponse.meta.totalCount
    override val blogList: List<Blog>
        get() = kakaoBlogSearchResponse.documents.map { KakaoBlogImpl(it) }
}
