package bj.max.lim.blog.search.domain.repository

import bj.max.lim.blog.search.domain.aggregate.KeywordRank

interface KeywordRankRepository {
    fun findTop10Keywords(): List<KeywordRank>
}
