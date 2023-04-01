package bj.max.lim.blog.search.outbound.webclient.request

import com.google.common.annotations.VisibleForTesting

data class BlogSearchClientRequest(
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

fun BlogSearchClientRequest.mapToKakaoBlogSearchRequest(): KakaoBlogSearchClientRequest {
    return KakaoBlogSearchClientRequest(
        query = this.query,
        page = this.page,
        size = pageSize,
        sort = when (blogSearchSortingOption) {
            BlogSearchClientRequest.BlogSearchSortingOption.ACCURACY_FIRST -> bj.max.lim.blog.search.outbound.webclient.request.KakaoBlogSearchClientRequest.SortingOption.ACCURACY_FIRST
            BlogSearchClientRequest.BlogSearchSortingOption.RECENCY_FIRST -> bj.max.lim.blog.search.outbound.webclient.request.KakaoBlogSearchClientRequest.SortingOption.RECENCY_FIRST
        }
    )
}

fun BlogSearchClientRequest.mapToNaverBlogSearchRequest(): NaverBlogSearchClientRequest {
    return NaverBlogSearchClientRequest(
        query = this.query,
        display = this.pageSize,
        start = (this.page - 1) * this.pageSize + 1,
        sort = when (blogSearchSortingOption) {
            BlogSearchClientRequest.BlogSearchSortingOption.ACCURACY_FIRST -> bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchClientRequest.SortingOption.ACCURACY_FIRST
            BlogSearchClientRequest.BlogSearchSortingOption.RECENCY_FIRST -> bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchClientRequest.SortingOption.RECENCY_FIRST
        }
    )
}
