package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogItem
import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogSearchResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class NaverBlogSearchImplTest {

    @Test
    fun `NaverBlogSearchImpl test`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

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

        // when
        val result = NaverBlogSearchImpl(naverBlogSearchResponse)

        // then
        assertThat(result.total).isEqualTo(naverBlogSearchResponse.total)
        assertThat(result.blogList).hasSize(2)
    }
}
