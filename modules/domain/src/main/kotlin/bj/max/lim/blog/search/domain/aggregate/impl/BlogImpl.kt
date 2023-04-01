package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.outbound.webclient.response.BlogClientResponse
import java.time.LocalDate

class BlogImpl(
    private val blogClientResponse: BlogClientResponse,
) : Blog {
    override val title: String
        get() = blogClientResponse.title
    override val blogName: String
        get() = blogClientResponse.blogName
    override val url: String
        get() = blogClientResponse.url
    override val description: String
        get() = blogClientResponse.description
    override val postDate: LocalDate
        get() = blogClientResponse.postDate
}
