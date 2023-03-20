package bj.max.lim.blog.search.outbound.webclient.request

import com.google.common.annotations.VisibleForTesting
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0
 * */
data class NaverBlogSearchRequest(
    // 검색어. UTF-8로 인코딩되어야 합니다.
    val query: String,

    // 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
    val display: Int = 10,

    // 검색 시작 위치(기본값: 1, 최댓값: 1000)
    val start: Int = 1,

    // 검색 결과 정렬 방법
    // - sim: 정확도순으로 내림차순 정렬(기본값)
    // - date: 날짜순으로 내림차순 정렬
    val sort: SortingOption = SortingOption.ACCURACY_FIRST,
) {
    enum class SortingOption(val alias: String) {
        ACCURACY_FIRST("sim"),
        RECENCY_FIRST("date")
    }

    init {
        assert(display in 1..MAX_DISPLAY) {
            DISPLAY_OUT_OF_RANGE_MESSAGE
        }
        assert(start in 1..MAX_START) {
            START_OUT_OF_RANGE_MESSAGE
        }
    }

    fun toQueryParams(): MultiValueMap<String, String> {
        val queryMap = LinkedMultiValueMap<String, String>()
        queryMap.add("query", query)
        queryMap.add("display", display.toString())
        queryMap.add("start", start.toString())
        queryMap.add("sort", sort.alias)
        return queryMap
    }

    @VisibleForTesting
    companion object {
        val MAX_DISPLAY = 100
        val MAX_START = 1000
        val DISPLAY_OUT_OF_RANGE_MESSAGE = "display는 1 ~ $MAX_DISPLAY 범위여야 합니다."
        val START_OUT_OF_RANGE_MESSAGE = "start는 1 ~ $MAX_START 범위여야 합니다."
    }
}
