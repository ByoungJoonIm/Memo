package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.NaverBlogItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NaverBlogImplTest {

    @Test
    fun `NaverBlogImpl test`() {
        // given
        val naverBlogItem = NaverBlogItem(
            title = "떡볶이",
            link = "https://blog.naver.com/abc/1241341",
            description = "떡볶이가 없는 세상을 상상해본 적이 있나요? 교촌치킨과 함께 드셔보셨나요?",
            bloggerName = "떡볶이 마당",
            bloggerLink = "https://blog.naver.com/abc",
            postDate = LocalDate.parse("2023-03-19")
        )

        // when
        val result = NaverBlogImpl(naverBlogItem)

        // then
        assertThat(result.title).isEqualTo(naverBlogItem.title)
        assertThat(result.url).isEqualTo(naverBlogItem.link)
        assertThat(result.description).isEqualTo(naverBlogItem.description)
        assertThat(result.blogName).isEqualTo(naverBlogItem.bloggerName)
        assertThat(result.postDate).isEqualTo(naverBlogItem.postDate)
    }
}
