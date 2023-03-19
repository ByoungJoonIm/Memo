package bj.max.lim.blog.search.domain.aggregate

interface KeywordRank {
    // 검색한 키워드
    val keyword: String

    // 키워드 호출 횟수
    val count: Long
}
