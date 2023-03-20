package bj.max.lim.blog.search.outbound.webclient.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

/**
 * https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog-response
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoBlogSearchResponse(
    val meta: Meta,
    val documents: List<KakaoDocument>,
)

data class Meta(
    // 검색된 문서 수
    @JsonProperty("total_count")
    val totalCount: Int,

    // total_count 중 노출 가능 문서 수
    @JsonProperty("pageable_count")
    val pageableCount: Int,

    // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
    @JsonProperty("is_end")
    val isEnd: Boolean,
)

data class KakaoDocument(
    // 블로그 글 제목
    val title: String,

    // 블로그 글 요약
    val contents: String,

    // 블로그 글 URL
    val url: String,

    // 블로그의 이름
    @JsonProperty("blogname")
    val blogName: String,

    // 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    val thumbnail: String,

    // 블로그 글 작성시간, ISO 8601
    // [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    val datetime: Instant,
)
