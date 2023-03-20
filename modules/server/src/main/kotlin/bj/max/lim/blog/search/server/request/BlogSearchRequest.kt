package bj.max.lim.blog.search.server.request

import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import com.google.common.annotations.VisibleForTesting

data class BlogSearchRequest(
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

    enum class BlogSearchSortingOption(val alias: String) {
        ACCURACY_FIRST("accuracy"),
        RECENCY_FIRST("recency"),
        ;

        companion object {
            fun fromAlias(alias: String): BlogSearchSortingOption {
                return BlogSearchSortingOption.values().first { it.alias == alias }
            }
        }
    }

    @VisibleForTesting
    companion object {
        internal val MAX_PAGE = 50
        internal val MAX_PAGE_SIZE = 20
        val PAGE_OUT_OF_RANGE_MESSAGE = "page 1 ~ $MAX_PAGE 범위여야 합니다."
        val PAGE_SIZE_OUT_OF_RANGE_MESSAGE = "size는 1 ~ $MAX_PAGE_SIZE 범위여야 합니다."
    }
}

fun BlogSearchRequest.mapToBlogSearchContext(): BlogSearchContext {
    return BlogSearchContext(
        query = this.query,
        page = this.page,
        pageSize = this.pageSize,
        blogSearchSortingOption = when (this.blogSearchSortingOption) {
            BlogSearchRequest.BlogSearchSortingOption.ACCURACY_FIRST -> BlogSearchContext.BlogSearchSortingOption.ACCURACY_FIRST
            BlogSearchRequest.BlogSearchSortingOption.RECENCY_FIRST -> BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST
        },
    )
}
