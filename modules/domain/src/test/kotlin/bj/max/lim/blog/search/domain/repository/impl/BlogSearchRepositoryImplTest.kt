package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.common.exception.AllClientUnavailableException
import bj.max.lim.blog.search.common.exception.Client5XXException
import bj.max.lim.blog.search.domain.aggregate.impl.KakaoBlogSearchImpl
import bj.max.lim.blog.search.domain.aggregate.impl.NaverBlogSearchImpl
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchResponse
import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import bj.max.lim.blog.search.outbound.webclient.response.Meta
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogItem
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchResponse
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.willReturn
import org.mockito.kotlin.willThrow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class BlogSearchRepositoryImplTest {

    lateinit var kakaoBlogSearchClientRepositoryImpl: KakaoBlogSearchClientRepositoryImpl
    lateinit var naverBlogSearchClientRepositoryImpl: NaverBlogSearchClientRepositoryImpl
    lateinit var blogSearchRepository: BlogSearchRepository

    @BeforeEach
    fun setUp() {
        kakaoBlogSearchClientRepositoryImpl = mock()
        naverBlogSearchClientRepositoryImpl = mock()
        blogSearchRepository = BlogSearchRepositoryImpl(
            listOf(
                kakaoBlogSearchClientRepositoryImpl,
                naverBlogSearchClientRepositoryImpl,
            )
        )
    }

    @Test
    fun `findByBlogSearchContext - 예외가 발생하지 않는 정상 케이스`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 1,
            pageSize = 2,
        )
        val kakaoBlogSearchResponse = KakaoBlogSearchResponse(
            meta = Meta(
                totalCount = 1000,
                pageableCount = 10,
                isEnd = false,
            ),
            documents = listOf(
                KakaoDocument(
                    title = "오늘도 떡볶이 한그릇",
                    contents = "오늘 하루는 가볍게 떡볶이 한그릇으로 마무리 해보세요~ 누가먹어도 맛있는 레시피를 알려드립니다~!",
                    url = "https://blog.kakao.com/1534132",
                    blogName = "매일떡볶이",
                    thumbnail = "https://blog.kakao.com/thumbnail/1534132",
                    datetime = now,
                ),
                KakaoDocument(
                    title = "오늘도 치킨 한그릇",
                    contents = "오늘 하루는 가볍게 치킨 한그릇으로 마무리 해보세요~ 누가먹어도 맛있는 레시피를 알려드립니다~!",
                    url = "https://blog.kakao.com/15341323",
                    blogName = "매일치킨",
                    thumbnail = "https://blog.kakao.com/thumbnail/15341323",
                    datetime = now,
                )
            )
        )
        val blogSearch = KakaoBlogSearchImpl(
            kakaoBlogSearchResponse
        )

        given {
            runBlocking {
                kakaoBlogSearchClientRepositoryImpl.findByBlogSearchContext(any<BlogSearchContext>())
            }
        } willReturn { blogSearch }

        // when
        val result = runBlocking {
            blogSearchRepository.findByBlogSearchContext(blogSearchContext)
        }

        // then
        assertThat(result.blogList).hasSize(2)
        runBlocking {
            verify(kakaoBlogSearchClientRepositoryImpl, times(1)).findByBlogSearchContext(any())
            verify(naverBlogSearchClientRepositoryImpl, times(0)).findByBlogSearchContext(any())
        }
    }

    @Test
    fun `findByBlogSearchContext - 카카오 블로그 장애시 네이버 블로그에서 결과를 반환해야 한다`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 1,
            pageSize = 2,
        )
        val naverBlogSearchResponse = NaverBlogSearchResponse(
            lastBuildDate = now,
            total = 12414,
            start = 1,
            display = 2,
            items = listOf(
                NaverBlogItem(
                    title = "떡볶이",
                    link = "https://blog.naver.com/abc/1241341",
                    description = "떡볶이가 없는 세상을 상상해본 적이 있나요? 교촌치킨과 함께 드셔보셨나요?",
                    bloggerName = "떡볶이 마당",
                    bloggerLink = "https://blog.naver.com/abc",
                    postDate = LocalDate.parse("2023-03-16")
                ),
                NaverBlogItem(
                    title = "떡볶이2",
                    link = "https://blog.naver.com/abc/1241342",
                    description = "떡볶이가 좋아!!",
                    bloggerName = "떡볶이 뒷마당",
                    bloggerLink = "https://blog.naver.com/abd",
                    postDate = LocalDate.parse("2023-03-19")
                )
            )
        )
        val blogSearch = NaverBlogSearchImpl(
            naverBlogSearchResponse
        )

        given {
            runBlocking {
                kakaoBlogSearchClientRepositoryImpl.findByBlogSearchContext(any<BlogSearchContext>())
            }
        } willThrow { Client5XXException("카카오 블로그 장애") }
        given {
            runBlocking {
                naverBlogSearchClientRepositoryImpl.findByBlogSearchContext(any<BlogSearchContext>())
            }
        } willReturn { blogSearch }

        // when
        val result = runBlocking {
            blogSearchRepository.findByBlogSearchContext(blogSearchContext)
        }

        // then
        assertThat(result.blogList).hasSize(2)
        runBlocking {
            verify(kakaoBlogSearchClientRepositoryImpl, times(1)).findByBlogSearchContext(any())
            verify(naverBlogSearchClientRepositoryImpl, times(1)).findByBlogSearchContext(any())
        }
    }

    @Test
    fun `findByBlogSearchContext - 카카오와 네이버 블로그 장애시 AllClientUnavailableException를 발생시킨다`() {
        // given
        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 1,
            pageSize = 2,
        )

        given {
            runBlocking {
                kakaoBlogSearchClientRepositoryImpl.findByBlogSearchContext(any<BlogSearchContext>())
            }
        } willThrow { Client5XXException("카카오 블로그 장애") }
        given {
            runBlocking {
                naverBlogSearchClientRepositoryImpl.findByBlogSearchContext(any<BlogSearchContext>())
            }
        } willThrow { Client5XXException("네이버 블로그 장애") }

        // when
        val result = runBlocking {
            assertThrows<AllClientUnavailableException> {
                blogSearchRepository.findByBlogSearchContext(blogSearchContext)
            }
        }

        // then
        runBlocking {
            verify(kakaoBlogSearchClientRepositoryImpl, times(1)).findByBlogSearchContext(any())
            verify(naverBlogSearchClientRepositoryImpl, times(1)).findByBlogSearchContext(any())
        }
    }
}
