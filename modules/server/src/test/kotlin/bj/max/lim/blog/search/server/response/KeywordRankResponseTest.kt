package bj.max.lim.blog.search.server.response

import bj.max.lim.blog.search.domain.aggregate.KeywordRank
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KeywordRankResponseTest {

    @Test
    fun `mapToKeywordRankResponse`() {
        // given
        val keywordRankList = listOf(
            object : KeywordRank {
                override val keyword: String
                    get() = "떡볶이"
                override val count: Long
                    get() = 10
            },
            object : KeywordRank {
                override val keyword: String
                    get() = "치킨"
                override val count: Long
                    get() = 4
            },
        )

        // when
        val result = keywordRankList.mapToKeywordRankResponse()

        // then
        assertThat(result.keywordRankList).hasSize(keywordRankList.size)
        assertThat(result.keywordRankList.map { it.keyword }).containsExactlyElementsOf(keywordRankList.map { it.keyword })
        assertThat(result.keywordRankList.map { it.count }).containsExactlyElementsOf(keywordRankList.map { it.count })
    }
}
