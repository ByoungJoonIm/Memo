package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogItem
import java.time.LocalDate

class NaverBlogImpl(
    private val naverBlogItem: NaverBlogItem,
) : Blog {
    override val title: String
        get() = naverBlogItem.title
    override val blogName: String
        get() = naverBlogItem.bloggerName
    override val url: String
        get() = naverBlogItem.link
    override val description: String
        get() = naverBlogItem.description
    override val postDate: LocalDate
        get() = naverBlogItem.postDate
}
