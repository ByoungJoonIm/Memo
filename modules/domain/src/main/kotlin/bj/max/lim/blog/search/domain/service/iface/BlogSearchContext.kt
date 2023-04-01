package bj.max.lim.blog.search.domain.service.iface

import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import com.google.common.annotations.VisibleForTesting

/**
 * kakao보다 naver가 조회 범위가 적습니다.
 * kakao: 50 * 50 = 2500
 * naver: 1000 + 100 = 1100
 * spec은 작은쪽으로 맞춥니다.
 * */
data class BlogSearchContext(
    // 검색어
    val query: String,

    // 검색 시작 페이지 위치 (1~50) default 1
    val page: Int = 1,

    // 페이지 사이즈(1~20) default 10
    val pageSize: Int = 10,

    // sorting 방식(정확도우선 / 최신 데이터 우선) default ACCURACY_FIRST
    val blogSearchSortingOption: BlogSearchSortingOption = BlogSearchSortingOption.ACCURACY_FIRST,
) {

    init {
        assert(page in 1..MAX_PAGE) {
            PAGE_OUT_OF_RANGE_MESSAGE
        }
        assert(pageSize in 1..MAX_PAGE_SIZE) {
            PAGE_SIZE_OUT_OF_RANGE_MESSAGE
        }
    }

    enum class BlogSearchSortingOption {
        ACCURACY_FIRST,
        RECENCY_FIRST,
    }

    @VisibleForTesting
    companion object {
        internal val MAX_PAGE = 50
        internal val MAX_PAGE_SIZE = 20
        val PAGE_OUT_OF_RANGE_MESSAGE = "page 1 ~ $MAX_PAGE 범위여야 합니다."
        val PAGE_SIZE_OUT_OF_RANGE_MESSAGE = "size는 1 ~ $MAX_PAGE_SIZE 범위여야 합니다."
    }
}

fun BlogSearchContext.mapToBlogSearchClientRequest(): BlogSearchClientRequest {
    return BlogSearchClientRequest(
        query = query,
        page = page,
        pageSize = pageSize,
        blogSearchSortingOption = when (blogSearchSortingOption) {
            BlogSearchContext.BlogSearchSortingOption.ACCURACY_FIRST -> BlogSearchClientRequest.BlogSearchSortingOption.ACCURACY_FIRST
            BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST -> BlogSearchClientRequest.BlogSearchSortingOption.RECENCY_FIRST
        }
    )
}
