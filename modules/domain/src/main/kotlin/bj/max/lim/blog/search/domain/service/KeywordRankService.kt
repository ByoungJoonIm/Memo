package bj.max.lim.blog.search.domain.service

import bj.max.lim.blog.search.domain.aggregate.KeywordRank
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import org.springframework.stereotype.Service

@Service
class KeywordRankService(
    private val keywordRankRepository: KeywordRankRepository,
) {

    fun findTop10Keywords(): List<KeywordRank> {
        return keywordRankRepository.findTop10Keywords()
    }
}
