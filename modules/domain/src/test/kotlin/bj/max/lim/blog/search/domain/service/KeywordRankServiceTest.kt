package bj.max.lim.blog.search.domain.service

import bj.max.lim.blog.search.domain.aggregate.impl.KeywordRankImpl
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.willReturn
import java.time.LocalDateTime

class KeywordRankServiceTest {

    lateinit var keywordRankRepository: KeywordRankRepository
    lateinit var keywordRankService: KeywordRankService

    @BeforeEach
    fun setUp() {
        keywordRankRepository = mock()
        keywordRankService = KeywordRankService(keywordRankRepository)
    }

    @Test
    fun `findTo10Keywords`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00")

        val keywordRankEntity = KeywordRankEntity(
            id = null,
            keyword = "떡볶이",
            count = 10,
            createdAt = now,
            updatedAt = now,
        )
        val keywordRankList = listOf(KeywordRankImpl(keywordRankEntity))

        given { keywordRankRepository.findTop10Keywords() } willReturn { keywordRankList }

        // when
        val result = keywordRankService.findTop10Keywords()

        // then
        assertThat(result).hasSize(1)
    }
}
