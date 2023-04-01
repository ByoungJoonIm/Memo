package bj.max.lim.blog.search.outbound.webclient.response

import java.time.LocalDate

interface BlogSearchClientResponse {
    // 검색된 결과 갯수
    val total: Int

    // 검색 결과
    val blogList: List<BlogClientResponse>
}

interface BlogClientResponse {
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
