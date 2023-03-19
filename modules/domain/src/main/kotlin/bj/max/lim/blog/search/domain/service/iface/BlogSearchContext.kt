package bj.max.lim.blog.search.domain.service.iface

import bj.max.lim.blog.search.outbound.webclient.request.KakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchRequest

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
    enum class BlogSearchSortingOption {
        ACCURACY_FIRST,
        RECENCY_FIRST,
    }
}

fun BlogSearchContext.mapToKakaoBlogSearchRequest(): KakaoBlogSearchRequest {
    return KakaoBlogSearchRequest(
        query = this.query,
        page = this.page,
        size = pageSize,
        sort = when (blogSearchSortingOption) {
            BlogSearchContext.BlogSearchSortingOption.ACCURACY_FIRST -> KakaoBlogSearchRequest.SortingOption.ACCURACY_FIRST
            BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST -> KakaoBlogSearchRequest.SortingOption.RECENCY_FIRST
        }
    )
}

fun BlogSearchContext.mapToNaverBlogSearchRequest(): NaverBlogSearchRequest {
    return NaverBlogSearchRequest(
        query = this.query,
        display = this.pageSize,
        start = (this.page - 1) * this.pageSize + 1,
        sort = when (blogSearchSortingOption) {
            BlogSearchContext.BlogSearchSortingOption.ACCURACY_FIRST -> NaverBlogSearchRequest.SortingOption.ACCURACY_FIRST
            BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST -> NaverBlogSearchRequest.SortingOption.RECENCY_FIRST
        }
    )
}
