package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.aggregate.KeywordRank
import bj.max.lim.blog.search.domain.aggregate.impl.KeywordRankImpl
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.outbound.database.repository.KeywordRankEntityRepository
import org.springframework.stereotype.Repository

@Repository
class KeywordRankRepositoryImpl(
    private val keywordRankEntityRepository: KeywordRankEntityRepository,
) : KeywordRankRepository {
    override fun findTop10Keywords(): List<KeywordRank> {
        return keywordRankEntityRepository.findTop10ByOrderByCountDesc().map { KeywordRankImpl(it) }
    }
}
