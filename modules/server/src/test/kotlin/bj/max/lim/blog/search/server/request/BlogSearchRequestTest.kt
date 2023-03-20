package bj.max.lim.blog.search.server.request

import bj.max.lim.blog.search.domain.service.iface.BlogSearchContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlogSearchRequestTest {

    @Test
    fun `mapToBlogSearchContext`() {
        // given
        val blogSearchRequest = BlogSearchRequest(
            query = "떡볶이",
            page = 3,
            pageSize = 15,
            blogSearchSortingOption = BlogSearchRequest.BlogSearchSortingOption.ACCURACY_FIRST
        )

        // when
        val result = blogSearchRequest.mapToBlogSearchContext()

        // then
        assertThat(result.query).isEqualTo(blogSearchRequest.query)
        assertThat(result.page).isEqualTo(blogSearchRequest.page)
        assertThat(result.pageSize).isEqualTo(blogSearchRequest.pageSize)
        assertThat(result.blogSearchSortingOption).isEqualTo(BlogSearchContext.BlogSearchSortingOption.ACCURACY_FIRST)
    }
}
