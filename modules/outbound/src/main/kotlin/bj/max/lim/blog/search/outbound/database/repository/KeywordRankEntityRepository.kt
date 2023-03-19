package bj.max.lim.blog.search.outbound.database.repository

import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRankEntityRepository : JpaRepository<KeywordRankEntity, Int> {
    fun findTop10ByOrderByCountDesc(): List<KeywordRankEntity>
    fun findByKeyword(keyword: String): KeywordRankEntity?
}
