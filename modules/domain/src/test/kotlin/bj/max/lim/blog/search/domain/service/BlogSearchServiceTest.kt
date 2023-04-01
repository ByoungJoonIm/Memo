package bj.max.lim.blog.search.domain.service

import bj.max.lim.blog.search.domain.aggregate.impl.BlogSearchImpl
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.repository.KeywordRankRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchClientResponse
import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import bj.max.lim.blog.search.outbound.webclient.response.Meta
import kotlinx.coroutines.runBlocking
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
import java.time.ZoneId

class BlogSearchServiceTest {

    lateinit var blogSearchRepository: BlogSearchRepository
    lateinit var keywordRankRepository: KeywordRankRepository
    lateinit var blogSearchService: BlogSearchService

    @BeforeEach
    fun setUp() {
        blogSearchRepository = mock()
        keywordRankRepository = mock()
        blogSearchService = BlogSearchService(blogSearchRepository, keywordRankRepository)
    }

    @Test
    fun `blogSearch`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 1,
            pageSize = 2,
        )
        val kakaoBlogSearchResponse = KakaoBlogSearchClientResponse(
            meta = Meta(
                totalCount = 1000,
                pageableCount = 10,
                isEnd = false,
            ),
            documents = listOf(
                KakaoDocument(
                    title = "오늘도 떡볶이 한그릇",
                    description = "오늘 하루는 가볍게 떡볶이 한그릇으로 마무리 해보세요~ 누가먹어도 맛있는 레시피를 알려드립니다~!",
                    url = "https://blog.kakao.com/1534132",
                    blogName = "매일떡볶이",
                    thumbnail = "https://blog.kakao.com/thumbnail/1534132",
                    datetime = now,
                ),
                KakaoDocument(
                    title = "오늘도 치킨 한그릇",
                    description = "오늘 하루는 가볍게 치킨 한그릇으로 마무리 해보세요~ 누가먹어도 맛있는 레시피를 알려드립니다~!",
                    url = "https://blog.kakao.com/15341323",
                    blogName = "매일치킨",
                    thumbnail = "https://blog.kakao.com/thumbnail/15341323",
                    datetime = now,
                )
            )
        )
        val blogSearch = BlogSearchImpl(
            kakaoBlogSearchResponse
        )

        given {
            runBlocking {
                blogSearchRepository.findByBlogSearchContext(any())
            }
        } willReturn { blogSearch }

        // when
        val result = runBlocking {
            blogSearchService.blogSearch(blogSearchContext)
        }

        // then
        assertThat(result.blogList).hasSize(2)

        val keywordRankRepositoryArgumentCaptor = argumentCaptor<String>()
        verify(keywordRankRepository, times(1)).incrementCountByKeyword(keywordRankRepositoryArgumentCaptor.capture())
        assertThat(keywordRankRepositoryArgumentCaptor.firstValue).isEqualTo(blogSearchContext.query)

        val blogSearchRepositoryArgumentCaptor = argumentCaptor<BlogSearchContext>()
        runBlocking {
            verify(blogSearchRepository, times(1)).findByBlogSearchContext(blogSearchRepositoryArgumentCaptor.capture())
        }
        assertThat(blogSearchRepositoryArgumentCaptor.firstValue).isEqualTo(blogSearchContext)
    }
}
