package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import java.time.LocalDate
import java.time.ZoneId

class KakaoBlogImpl(
    private val kakaoDocument: KakaoDocument,
) : Blog {
    override val title: String
        get() = kakaoDocument.title
    override val blogName: String
        get() = kakaoDocument.blogName
    override val url: String
        get() = kakaoDocument.url
    override val description: String
        get() = kakaoDocument.contents
    override val postDate: LocalDate
        get() = LocalDate.ofInstant(kakaoDocument.datetime, ZoneId.of("Asia/Seoul"))
}
