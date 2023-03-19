package bj.max.lim.blog.search.outbound.database.repository

import bj.max.lim.blog.search.outbound.database.model.KeywordRankEntity
import bj.max.lim.blog.search.outbound.webclient.AbstractSpringTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KeywordRankEntityRepositoryTest : AbstractSpringTest() {

    @Autowired
    lateinit var keywordRankEntityRepository: KeywordRankEntityRepository

    @AfterEach
    fun after() {
        keywordRankEntityRepository.deleteAll()
    }

    @Test
    fun `findTop10ByOrderByCountDesc - rank로 잘 가져와 지는지 테스트`() {
        // given
        // keyword1, 1
        // keyword2, 2
        // ...
        var entities = mutableListOf<KeywordRankEntity>()

        for (i in 1..15) {
            entities.add(
                KeywordRankEntity(
                    id = null,
                    keyword = "keyword$i",
                    count = i.toLong(),
                )
            )
        }

        repeat(11) {
        }
        keywordRankEntityRepository.saveAll(entities)

        // when
        val result = keywordRankEntityRepository.findTop10ByOrderByCountDesc()

        // then
        assertThat(result).hasSize(10)
        assertThat(result.map { it.count.toInt() }).isEqualTo((6..15).toList().reversed())
    }

    @Test
    fun `findTop10ByOrderByCountDesc - rank data가 얼마 없을때도 잘 동작해야 한다`() {
        // given
        // keyword1, 1
        // keyword2, 2
        // ...
        var entities = mutableListOf<KeywordRankEntity>()
        for (i in 1..5) {
            entities.add(
                KeywordRankEntity(
                    id = null,
                    keyword = "keyword$i",
                    count = i.toLong(),
                )
            )
        }
        keywordRankEntityRepository.saveAll(entities)

        // when
        val result = keywordRankEntityRepository.findTop10ByOrderByCountDesc()

        // then
        assertThat(result).hasSize(5)
        assertThat(result.map { it.count.toInt() }).isEqualTo((1..5).toList().reversed())
    }

    @Test
    fun `findByKeyword - keyword가 있는 경우 조회`() {
        // given
        val entity = KeywordRankEntity(
            id = null,
            keyword = "keyword",
            count = 10L,
        )
        keywordRankEntityRepository.save(entity)

        // when
        val result = keywordRankEntityRepository.findByKeyword("keyword")

        // then
        assertThat(result!!.keyword).isEqualTo("keyword")
        assertThat(result!!.count).isEqualTo(10L)
    }

    @Test
    fun `findByKeyword - keyword가 없는 경우 조회`() {
        // given
        // when
        val result = keywordRankEntityRepository.findByKeyword("keyword")

        // then
        assertThat(result).isNull()
    }
}
