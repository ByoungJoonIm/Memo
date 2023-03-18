package bj.max.lim.blog.search.outbound.webclient.request

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog-request
 * */
data class KakaoBlogSearchRequest(
    // 검색을 원하는 질의어
    // 특정 블로그 글만 검색하고 싶은 경우, 블로그 url과 검색어를 공백(' ') 구분자로 넣을 수 있음
    val query: String,

    // 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
    val sort: SortingOption = SortingOption.ACCURACY_FIRST,

    // 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
    val page: Int = 1,

    // 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
    val size: Int = 10,
) {
    enum class SortingOption(val alias: String) {
        ACCURACY_FIRST("accuracy"),
        RECENCY_FIRST("recency")
    }

    init {
        assert(page in 1..50) {
            PAGE_OUT_OF_RANGE_MESSAGE
        }
        assert(size in 1..50) {
            SIZE_OUT_OF_RANGE_MESSAGE
        }
    }

    fun toQueryParams(): MultiValueMap<String, String> {
        val queryMap = LinkedMultiValueMap<String, String>()
        queryMap.add("query", query)
        queryMap.add("sort", sort.alias)
        queryMap.add("page", page.toString())
        queryMap.add("size", size.toString())
        return queryMap
    }

    companion object {
        val PAGE_OUT_OF_RANGE_MESSAGE = "page 1 ~ 50 범위여야 합니다."
        val SIZE_OUT_OF_RANGE_MESSAGE = "size는 1 ~ 50 범위여야 합니다."
    }
}
