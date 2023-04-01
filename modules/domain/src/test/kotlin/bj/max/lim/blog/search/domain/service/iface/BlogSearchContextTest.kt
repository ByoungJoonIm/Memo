package bj.max.lim.blog.search.domain.service.iface

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BlogSearchContextTest {

    @Test
    fun `BlogSearchContext - 정상 동작`() {
        // then
        assertDoesNotThrow {
            // when
            BlogSearchContext(
                query = "떡볶이",
                page = 3,
                pageSize = 10,
                blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST
            )
        }
    }

    @Test
    fun `BlogSearchContext - page 범위 초과`() {
        // when
        val result = assertThrows<AssertionError> {
            BlogSearchContext(
                query = "떡볶이",
                page = 60,
                pageSize = 10,
                blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST
            )
        }

        // then
        assertThat(result.message).isEqualTo(BlogSearchContext.PAGE_OUT_OF_RANGE_MESSAGE)
    }

    @Test
    fun `BlogSearchContext - pageSize 범위 초과`() {
        // when
        val result = assertThrows<AssertionError> {
            BlogSearchContext(
                query = "떡볶이",
                page = 20,
                pageSize = 100,
                blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST
            )
        }

        // then
        assertThat(result.message).isEqualTo(BlogSearchContext.PAGE_SIZE_OUT_OF_RANGE_MESSAGE)
    }

    @Test
    fun `mapToBlogSearchClientRequest`() {
        // given
        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 3,
            pageSize = 15,
            blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST,
        )

        // when
        val result = blogSearchContext.mapToBlogSearchClientRequest()

        // then
        assertThat(result.query).isEqualTo(blogSearchContext.query)
        assertThat(result.page).isEqualTo(blogSearchContext.page)
        assertThat(result.pageSize).isEqualTo(blogSearchContext.pageSize)
        assertThat(result.blogSearchSortingOption.name).isEqualTo(blogSearchContext.blogSearchSortingOption.name)
    }
}
