package bj.max.lim.blog.search.domain.service.iface

import bj.max.lim.blog.search.outbound.webclient.request.KakaoBlogSearchRequest
import bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchRequest
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
    fun `mapToKakaoBlogSearchRequest`() {
        // given
        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 3,
            pageSize = 15,
            blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST,
        )

        // when
        val result = blogSearchContext.mapToKakaoBlogSearchRequest()

        // then
        assertThat(result.query).isEqualTo(blogSearchContext.query)
        assertThat(result.page).isEqualTo(blogSearchContext.page)
        assertThat(result.size).isEqualTo(blogSearchContext.pageSize)
        assertThat(result.sort).isEqualTo(KakaoBlogSearchRequest.SortingOption.RECENCY_FIRST)
    }

    @Test
    fun `mapToNaverBlogSearchRequest`() {
        // given
        val blogSearchContext = BlogSearchContext(
            query = "떡볶이",
            page = 3,
            pageSize = 15,
            blogSearchSortingOption = BlogSearchContext.BlogSearchSortingOption.RECENCY_FIRST,
        )

        // when
        val result = blogSearchContext.mapToNaverBlogSearchRequest()

        // then
        assertThat(result.query).isEqualTo(blogSearchContext.query)
        assertThat(result.display).isEqualTo(blogSearchContext.pageSize)
        assertThat(result.start).isEqualTo(31)
        assertThat(result.sort).isEqualTo(NaverBlogSearchRequest.SortingOption.RECENCY_FIRST)
    }
}
