package bj.max.lim.blog.search.outbound.webclient.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class NaverBlogSearchRequestTest {

    @Test
    fun `NaverBlogSearchRequest가 잘 만들어지는 케이스`() {
        // then
        assertDoesNotThrow {
            // when
            NaverBlogSearchClientRequest(
                query = "떡볶이",
                display = 10,
                start = 10,
                sort = NaverBlogSearchClientRequest.SortingOption.ACCURACY_FIRST,
            )
        }
    }

    @Test
    fun `NaverBlogSearchRequest에서 display range를 초과한 경우 예외가 발생`() {
        // when
        val result = assertThrows<java.lang.AssertionError> {
            NaverBlogSearchClientRequest(
                query = "떡볶이",
                display = 101,
                start = 10,
                sort = NaverBlogSearchClientRequest.SortingOption.ACCURACY_FIRST,
            )
        }

        // then
        assertThat(result.message).isEqualTo(NaverBlogSearchClientRequest.DISPLAY_OUT_OF_RANGE_MESSAGE)
    }

    @Test
    fun `NaverBlogSearchRequest에서 start range가 초과한 경우 예외가 발생`() {
        // when
        val result = assertThrows<java.lang.AssertionError> {
            NaverBlogSearchClientRequest(
                query = "떡볶이",
                display = 100,
                start = 0,
                sort = NaverBlogSearchClientRequest.SortingOption.ACCURACY_FIRST,
            )
        }

        // then
        assertThat(result.message).isEqualTo(NaverBlogSearchClientRequest.START_OUT_OF_RANGE_MESSAGE)
    }
}
