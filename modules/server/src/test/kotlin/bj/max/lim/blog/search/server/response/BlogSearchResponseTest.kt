package bj.max.lim.blog.search.server.response

import bj.max.lim.blog.search.domain.aggregate.Blog
import bj.max.lim.blog.search.domain.aggregate.BlogSearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BlogSearchResponseTest {

    @Test
    fun `mapToBlogSearchResponse`() {
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

        // when
        val result = blogSearch.mapToBlogSearchResponse()

        // then
        assertThat(result.total).isEqualTo(blogSearch.total)
        assertThat(result.blogList).hasSize(blogSearch.blogList.size)
        assertThat(result.blogList.first().title).isEqualTo(blogSearch.blogList.first().title)
        assertThat(result.blogList.first().blogName).isEqualTo(blogSearch.blogList.first().blogName)
        assertThat(result.blogList.first().url).isEqualTo(blogSearch.blogList.first().url)
        assertThat(result.blogList.first().description).isEqualTo(blogSearch.blogList.first().description)
        assertThat(result.blogList.first().postDate).isEqualTo(blogSearch.blogList.first().postDate)
    }
}
