package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KeywordRankImplTest {

    @Test
    fun `KeywordRankImpl test`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00")

        val keywordRankEntity = KeywordRankEntity(
            id = null,
            keyword = "떡볶이",
            count = 10,
            createdAt = now,
            updatedAt = now,
        )

        // when
        val result = KeywordRankImpl(keywordRankEntity)

        // then
        assertThat(result.keyword).isEqualTo(keywordRankEntity.keyword)
        assertThat(result.count).isEqualTo(keywordRankEntity.count)
    }
}
