package bj.max.lim.blog.search.domain.aggregate

import java.time.LocalDate

interface Blog {
    // 블로그 글 제목
    val title: String

    // 블로그 이름
    val blogName: String

    // 블로그 글 url
    val url: String

    // 블로그 글 요약
    val description: String

    // 블로그 글 작성 시간
    val postDate: LocalDate
}
