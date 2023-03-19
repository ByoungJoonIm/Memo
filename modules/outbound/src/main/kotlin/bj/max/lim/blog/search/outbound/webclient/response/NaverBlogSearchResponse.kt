package bj.max.lim.blog.search.outbound.webclient.response

import bj.max.lim.blog.search.common.Rfc1123Deserializer
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import java.time.Instant
import java.time.LocalDate

/**
 * https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EC%9D%91%EB%8B%B5
 * */
data class NaverBlogSearchResponse(
    // 검색 결과를 생성한 시간
    // Sat, 18 Mar 2023 20:02:50 +0900
    @JsonDeserialize(using = Rfc1123Deserializer::class)
    val lastBuildDate: Instant,

    // 총 검색 결과 개수
    val total: Int,

    // 	검색 시작 위치
    val start: Int,

    // 한 번에 표시할 검색 결과 개수
    val display: Int,

    // 검색 결과
    val items: List<NaverBlogItem>
)

data class NaverBlogItem(
    // 블로그 포스트의 제목. 제목에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    val title: String,

    // 블로그 포스트의 URL
    val link: String,

    // 블로그 포스트의 내용을 요약한 패시지 정보. 패시지 정보에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    val description: String,

    // 블로그 포스트가 있는 블로그의 이름
    @JsonProperty("bloggername")
    val bloggerName: String,

    // 블로그 포스트가 있는 블로그의 주소
    @JsonProperty("bloggerlink")
    val bloggerLink: String,

    // 블로그 포스트가 작성된 날짜
    @JsonProperty("postdate")
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @JsonFormat(pattern = "yyyyMMdd")
    val postDate: LocalDate,
)
