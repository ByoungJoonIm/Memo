package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.BlogClientResponse
import bj.max.lim.blog.search.outbound.webclient.response.BlogSearchClientResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BlogSearchImplTest {

    @Test
    fun `BlogSearchImpl test`() {
        // given
        val now = LocalDate.parse("2023-03-19")

        val blogSearchResponse = object : BlogSearchClientResponse {
            override val total: Int
                get() = 300
            override val blogList: List<BlogClientResponse>
                get() = listOf(
                    object : BlogClientResponse {
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
                    },
                    object : BlogClientResponse {
                        override val title: String
                            get() = "치킨 사랑"
                        override val blogName: String
                            get() = "치킨 왕자"
                        override val url: String
                            get() = "https://blog.kakao.com/1345151511"
                        override val description: String
                            get() = "치킨은 너무 맛있어요. 떡볶이랑 같이 먹어도 너무 맛있답니다."
                        override val postDate: LocalDate
                            get() = now
                    }
                )
        }

        // when
        val result = BlogSearchImpl(blogSearchResponse)

        // then
        assertThat(result.total).isEqualTo(blogSearchResponse.total)
        assertThat(result.blogList).hasSize(2)
    }
}
