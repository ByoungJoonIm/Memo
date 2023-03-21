package bj.max.lim.blog.search.server.response

import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class BlogSearchResponse(
    // 검색된 결과 갯수
    val total: Int,

    // 검색 결과
    val blogList: List<Blog>,
) {
    data class Blog(
        // 블로그 글 제목
        val title: String,

        // 블로그 이름
        val blogName: String,

        // 블로그 글 url
        val url: String,

        // 블로그 글 요약
        val description: String,

        // 블로그 글 작성 날짜
        @DateTimeFormat
        val postDate: LocalDate,
    )
}

fun BlogSearch.mapToBlogSearchResponse(): BlogSearchResponse {
    return BlogSearchResponse(
        total = this.total,
        blogList = this.blogList.map {
            BlogSearchResponse.Blog(
                title = it.title,
                blogName = it.blogName,
                url = it.url,
                description = it.description,
                postDate = it.postDate,
            )
        }
    )
}
