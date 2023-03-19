package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.domain.aggregate.KeywordRank
import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity

class KeywordRankImpl(
    private val keywordRankEntity: KeywordRankEntity,
) : KeywordRank {
    override val keyword: String
        get() = keywordRankEntity.keyword
    override val count: Long
        get() = keywordRankEntity.count
}
