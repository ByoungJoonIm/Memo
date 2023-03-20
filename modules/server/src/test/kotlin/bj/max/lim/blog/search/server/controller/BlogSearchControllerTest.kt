package bj.max.lim.blog.search.server.controller

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import bj.max.lim.blog.search.domain.service.BlogSearchService
import bj.max.lim.blog.search.domain.service.KeywordRankService
import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import bj.max.lim.blog.search.server.request.BlogSearchRequest
import bj.max.lim.blog.search.server.response.BlogSearchResponse
import bj.max.lim.blog.search.server.response.mapToBlogSearchResponse
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.willReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogSearchControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var blogSearchService: BlogSearchService

    @MockBean
    lateinit var keywordSearchService: KeywordRankService

    @Test
    fun `blogSearch - query만 있는 경우도 잘 동작해야 한다`() {
        // given
        val blogList = listOf(
            object : Blog {
                override val title: String
                    get() = "오늘도 떡볶이"
                override val blogName: String
                    get() = "떡복이 마녀"
                override val url: String
                    get() = "https://blog.kakao.com/14124"
                override val description: String
                    get() = "오늘도 즐거운 하루 보내셨나요? 하루를 마무리할 아름다운 떡볶이 레시피를 알려드릴게요!"
                override val postDate: LocalDate
                    get() = LocalDate.parse("2023-03-20")
            }
        )
        val blogSearch = object : BlogSearch {
            override val total: Int
                get() = 1351
            override val blogList: List<Blog>
                get() = blogList
        }

        given { runBlocking { blogSearchService.blogSearch(any<BlogSearchContext>()) } } willReturn { blogSearch }

        // when
        // then
        webTestClient
            .get()
            .uri { urlBuilder ->
                urlBuilder.path("/BlogSearch")
                    .queryParam("query", "떡볶이")
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogSearchResponse::class.java).value {
                assertThat(it).isEqualTo(blogSearch.mapToBlogSearchResponse())
            }

        val captor = argumentCaptor<BlogSearchContext>()
        runBlocking {
            verify(blogSearchService, times(1)).blogSearch(captor.capture())
        }
        assertThat(captor.firstValue.query).isEqualTo("떡볶이")
        assertThat(captor.firstValue.page).isEqualTo(1) // default value
        assertThat(captor.firstValue.pageSize).isEqualTo(10) // default value
        assertThat(captor.firstValue.blogSearchSortingOption.name).isEqualTo(BlogSearchRequest.BlogSearchSortingOption.ACCURACY_FIRST.name) // default value
    }

    @Test
    fun `blogSearch - 다양한 쿼리를 준 경우 잘 동작해야 한다`() {
        // given
        val blogList = listOf(
            object : Blog {
                override val title: String
                    get() = "오늘도 떡볶이"
                override val blogName: String
                    get() = "떡복이 마녀"
                override val url: String
                    get() = "https://blog.kakao.com/14124"
                override val description: String
                    get() = "오늘도 즐거운 하루 보내셨나요? 하루를 마무리할 아름다운 떡볶이 레시피를 알려드릴게요!"
                override val postDate: LocalDate
                    get() = LocalDate.parse("2023-03-20")
            }
        )
        val blogSearch = object : BlogSearch {
            override val total: Int
                get() = 1351
            override val blogList: List<Blog>
                get() = blogList
        }

        given { runBlocking { blogSearchService.blogSearch(any<BlogSearchContext>()) } } willReturn { blogSearch }

        // when
        // then
        webTestClient
            .get()
            .uri { urlBuilder ->
                urlBuilder.path("/BlogSearch")
                    .queryParam("query", "떡볶이")
                    .queryParam("sorting", "recency")
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogSearchResponse::class.java).value {
                assertThat(it).isEqualTo(blogSearch.mapToBlogSearchResponse())
            }

        val captor = argumentCaptor<BlogSearchContext>()
        runBlocking {
            verify(blogSearchService, times(1)).blogSearch(captor.capture())
        }
        assertThat(captor.firstValue.query).isEqualTo("떡볶이")
        assertThat(captor.firstValue.page).isEqualTo(1) // default value
        assertThat(captor.firstValue.pageSize).isEqualTo(10) // default value
        assertThat(captor.firstValue.blogSearchSortingOption.name).isEqualTo(BlogSearchRequest.BlogSearchSortingOption.RECENCY_FIRST.name) // default value
    }

    @Test
    fun `blogSearch - 모든 쿼리를 준 경우 잘 동작해야 한다`() {
        // given
        val blogList = listOf(
            object : Blog {
                override val title: String
                    get() = "오늘도 떡볶이"
                override val blogName: String
                    get() = "떡복이 마녀"
                override val url: String
                    get() = "https://blog.kakao.com/14124"
                override val description: String
                    get() = "오늘도 즐거운 하루 보내셨나요? 하루를 마무리할 아름다운 떡볶이 레시피를 알려드릴게요!"
                override val postDate: LocalDate
                    get() = LocalDate.parse("2023-03-20")
            }
        )
        val blogSearch = object : BlogSearch {
            override val total: Int
                get() = 1351
            override val blogList: List<Blog>
                get() = blogList
        }

        given { runBlocking { blogSearchService.blogSearch(any<BlogSearchContext>()) } } willReturn { blogSearch }

        // when
        // then
        webTestClient
            .get()
            .uri { urlBuilder ->
                urlBuilder.path("/BlogSearch")
                    .queryParam("query", "떡볶이")
                    .queryParam("page", "3")
                    .queryParam("pageSize", "17")
                    .queryParam("sorting", "recency")
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(BlogSearchResponse::class.java).value {
                assertThat(it).isEqualTo(blogSearch.mapToBlogSearchResponse())
            }

        val captor = argumentCaptor<BlogSearchContext>()
        runBlocking {
            verify(blogSearchService, times(1)).blogSearch(captor.capture())
        }
        assertThat(captor.firstValue.query).isEqualTo("떡볶이")
        assertThat(captor.firstValue.page).isEqualTo(3)
        assertThat(captor.firstValue.pageSize).isEqualTo(17)
        assertThat(captor.firstValue.blogSearchSortingOption.name).isEqualTo(BlogSearchRequest.BlogSearchSortingOption.RECENCY_FIRST.name) // default value
    }

    @Test
    fun `blogSearch - parameter가 잘못된경우 Bad Request 응답을 줘야한다`() {
        // given
        // when
        // then
        webTestClient
            .get()
            .uri { urlBuilder ->
                urlBuilder.path("/BlogSearch")
                    .queryParam("query", "떡볶이")
                    .queryParam("page", "100")
                    .queryParam("pageSize", "17")
                    .queryParam("sorting", "recency")
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun keywordRank() {
    }
}
