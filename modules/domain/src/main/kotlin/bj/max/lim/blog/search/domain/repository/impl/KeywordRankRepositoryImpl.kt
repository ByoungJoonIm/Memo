package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.KeywordRank
import bj.max.lim.blog.search.domain.aggregate.impl.KeywordRankImpl
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import bj.max.lim.blog.search.outbound.database.repository.KeywordRankEntityRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Repository
class KeywordRankRepositoryImpl(
    private val keywordRankEntityRepository: KeywordRankEntityRepository,
) : KeywordRankRepository {

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    override fun findTop10Keywords(): List<KeywordRank> {
        return keywordRankEntityRepository.findTop10ByOrderByCountDesc().map { KeywordRankImpl(it) }
    }

    @Transactional
    override fun incrementCountByKeyword(keyword: String): KeywordRank {
        val keywordRankEntity = keywordRankEntityRepository.findByKeyword(keyword)

        val incrementingKeywordRankEntity = if (keywordRankEntity == null) {
            KeywordRankEntity(
                id = null,
                keyword = keyword,
                count = 1
            )
        } else {
            keywordRankEntity.count++
            keywordRankEntity
        }
        val savedKeywordRankEntity = keywordRankEntityRepository.save(incrementingKeywordRankEntity)
        return KeywordRankImpl(savedKeywordRankEntity)
    }
}
