package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import bj.max.lim.blog.search.outbound.database.repository.KeywordRankEntityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.willReturn
import java.time.LocalDateTime

class KeywordRankRepositoryImplTest {

    lateinit var keywordRankRepository: KeywordRankRepository
    lateinit var keywordRankEntityRepository: KeywordRankEntityRepository

    @BeforeEach
    fun setUp() {
        keywordRankEntityRepository = mock()
        keywordRankRepository = KeywordRankRepositoryImpl(keywordRankEntityRepository)
    }

    @Test
    fun `findTop10Keywords`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00")

        val entities = listOf(
            KeywordRankEntity(
                id = 1,
                keyword = "떡볶이",
                count = 14,
                createdAt = now,
                updatedAt = now,
            ),
            KeywordRankEntity(
                id = 5,
                keyword = "치킨",
                count = 10,
                createdAt = now,
                updatedAt = now,
            )
        )

        given { keywordRankEntityRepository.findTop10ByOrderByCountDesc() } willReturn { entities }

        // when
        val result = keywordRankRepository.findTop10Keywords()

        // then
        assertThat(result).hasSize(2)
        assertThat(result.map { it.keyword }).containsExactlyElementsOf(listOf("떡볶이", "치킨"))
        assertThat(result.map { it.count }).containsExactlyElementsOf(listOf(14, 10))
    }

    @Test
    fun `incrementCountByKeyword - keywordRankEntity가 있는 경우`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00")

        val entity = KeywordRankEntity(
            id = 1,
            keyword = "떡볶이",
            count = 14,
            createdAt = now,
            updatedAt = now,
        )
        val expectedEntity = KeywordRankEntity(
            id = 2,
            keyword = "떡볶이",
            count = 14,
            createdAt = now,
            updatedAt = now,
        )
        given { keywordRankEntityRepository.findByKeyword(entity.keyword) } willReturn { entity }
        given { keywordRankEntityRepository.save(entity) } willReturn { expectedEntity }

        // when
        val result = keywordRankRepository.incrementCountByKeyword("떡볶이")

        // then
        assertThat(result.keyword).isSameAs(expectedEntity.keyword)
        assertThat(result.count).isSameAs(expectedEntity.count)
    }

    @Test
    fun `incrementCountByKeyword - keywordRankEntity가 없는 경우`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00")

        val expectedEntity = KeywordRankEntity(
            id = 2,
            keyword = "떡볶이",
            count = 1,
            createdAt = now,
            updatedAt = now,
        )
        given { keywordRankEntityRepository.findByKeyword(any()) } willReturn { null }
        given { keywordRankEntityRepository.save(any<KeywordRankEntity>()) } willReturn { expectedEntity }

        // when
        val result = keywordRankRepository.incrementCountByKeyword("떡볶이")

        // then
        assertThat(result.keyword).isSameAs(expectedEntity.keyword)
        assertThat(result.count).isSameAs(expectedEntity.count)

        val captor = argumentCaptor<KeywordRankEntity>()
        verify(keywordRankEntityRepository, times(1)).save(captor.capture())
        assertThat(captor.firstValue.keyword).isEqualTo("떡볶이")
        assertThat(captor.firstValue.count).isEqualTo(1)
    }
}
