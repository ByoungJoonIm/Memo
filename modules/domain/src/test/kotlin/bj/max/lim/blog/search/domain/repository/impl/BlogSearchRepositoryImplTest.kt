package bj.max.lim.blog.search.domain.repository.impl

import bj.max.lim.blog.search.common.exception.AllClientUnavailableException
import bj.max.lim.blog.search.common.exception.Client5XXException
import bj.max.lim.blog.search.domain.repository.BlogSearchRepository
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.outbound.webclient.client.KakaoBlogSearchClient
import bj.max.lim.blog.search.outbound.webclient.client.NaverBlogSearchClient
import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchClientResponse
import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import bj.max.lim.blog.search.outbound.webclient.response.Meta
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogItem
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchClientResponse
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

    lateinit var kakaoBlogSearchClient: KakaoBlogSearchClient
    lateinit var naverBlogSearchClient: NaverBlogSearchClient

    lateinit var blogSearchRepository: BlogSearchRepository

    @BeforeEach
    fun setUp() {
        kakaoBlogSearchClient = mock()
        naverBlogSearchClient = mock()
        blogSearchRepository = BlogSearchRepositoryImpl(
            listOf(
                kakaoBlogSearchClient,
                naverBlogSearchClient,
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

        runBlocking {
            given(kakaoBlogSearchClient.send(any<BlogSearchClientRequest>())) willReturn { kakaoBlogSearchResponse }
        }

        // when
        val result = runBlocking {
            blogSearchRepository.findByBlogSearchContext(blogSearchContext)
        }

        // then
        assertThat(result.blogList).hasSize(2)
        runBlocking {
            verify(kakaoBlogSearchClient, times(1)).send(any())
            verify(naverBlogSearchClient, times(0)).send(any())
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
        val naverBlogSearchResponse = NaverBlogSearchClientResponse(
            lastBuildDate = now,
            total = 12414,
            start = 1,
            display = 2,
            items = listOf(
                NaverBlogItem(
                    title = "떡볶이",
                    url = "https://blog.naver.com/abc/1241341",
                    description = "떡볶이가 없는 세상을 상상해본 적이 있나요? 교촌치킨과 함께 드셔보셨나요?",
                    blogName = "떡볶이 마당",
                    bloggerLink = "https://blog.naver.com/abc",
                    postDate = LocalDate.parse("2023-03-16")
                ),
                NaverBlogItem(
                    title = "떡볶이2",
                    url = "https://blog.naver.com/abc/1241342",
                    description = "떡볶이가 좋아!!",
                    blogName = "떡볶이 뒷마당",
                    bloggerLink = "https://blog.naver.com/abd",
                    postDate = LocalDate.parse("2023-03-19")
                )
            )
        )

        given {
            runBlocking {
                kakaoBlogSearchClient.send(any<BlogSearchClientRequest>())
            }
        } willThrow { Client5XXException("카카오 블로그 장애") }
        given {
            runBlocking {
                naverBlogSearchClient.send(any<BlogSearchClientRequest>())
            }
        } willReturn { naverBlogSearchResponse }

        // when
        val result = runBlocking {
            blogSearchRepository.findByBlogSearchContext(blogSearchContext)
        }

        // then
        assertThat(result.blogList).hasSize(2)
        runBlocking {
            verify(kakaoBlogSearchClient, times(1)).send(any())
            verify(naverBlogSearchClient, times(1)).send(any())
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
                kakaoBlogSearchClient.send(any<BlogSearchClientRequest>())
            }
        } willThrow { Client5XXException("카카오 블로그 장애") }
        given {
            runBlocking {
                naverBlogSearchClient.send(any<BlogSearchClientRequest>())
            }
        } willThrow { Client5XXException("네이버 블로그 장애") }

        // when
        runBlocking {
            assertThrows<AllClientUnavailableException> {
                blogSearchRepository.findByBlogSearchContext(blogSearchContext)
            }
        }

        // then
        runBlocking {
            verify(kakaoBlogSearchClient, times(1)).send(any())
            verify(naverBlogSearchClient, times(1)).send(any())
        }
    }
}
