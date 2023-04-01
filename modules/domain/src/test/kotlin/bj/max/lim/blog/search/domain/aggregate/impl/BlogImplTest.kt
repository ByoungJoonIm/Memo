package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.BlogClientResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BlogImplTest {

    @Test
    fun `BlogImpl test`() {
        // given
        val now = LocalDate.parse("2023-03-19")

        val blogClientResponse = object : BlogClientResponse {
            override val title: String
                get() = "떡볶이 사랑"
            override val blogName: String
                get() = "떡볶이 마녀"
            override val url: String
                get() = "https://blog.kakao.com/13451515"
            override val description: String
                get() = "떡볶이는 너무 맛있어요. 치킨이랑 같이 먹어도 너무 맛있답니다."
            override val postDate: LocalDate
                get() = now
        }

        // when
        val result = BlogImpl(blogClientResponse)

        // then
        assertThat(result.title).isEqualTo(blogClientResponse.title)
        assertThat(result.description).isEqualTo(blogClientResponse.description)
        assertThat(result.url).isEqualTo(blogClientResponse.url)
        assertThat(result.blogName).isEqualTo(blogClientResponse.blogName)
        assertThat(result.postDate).isEqualTo(blogClientResponse.postDate)
    }
}
