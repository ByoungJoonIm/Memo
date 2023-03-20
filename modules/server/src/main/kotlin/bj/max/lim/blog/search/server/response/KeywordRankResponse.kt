package bj.max.lim.blog.search.server.response

import bj.max.lim.blog.search.domain.aggregate.KeywordRank

data class KeywordRankResponse(
    val keywordRankList: List<KeywordRank>,
) {
    data class KeywordRank(
        // 검색한 키워드
        val keyword: String,

        // 키워드 호출 횟수
        val count: Long,
    )
}

fun List<KeywordRank>.mapToKeywordRankResponse(): KeywordRankResponse {
    return KeywordRankResponse(
        keywordRankList = this.map {
            KeywordRankResponse.KeywordRank(
                keyword = it.keyword,
                count = it.count,
            )
        }
    )
}
