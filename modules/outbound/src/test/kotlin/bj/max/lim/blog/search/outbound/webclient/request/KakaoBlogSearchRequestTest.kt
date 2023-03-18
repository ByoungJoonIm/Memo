package bj.max.lim.blog.search.outbound.webclient.request

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.AssertionError

class KakaoBlogSearchRequestTest {

    @Test
    fun `KakaoBlogSearchRequest가 잘 만들어지는 케이스`() {
        // then
        assertDoesNotThrow {
            // when
            KakaoBlogSearchRequest(
                query = "떡볶이",
                sort = KakaoBlogSearchRequest.SortingOption.ACCURACY_FIRST,
                page = 10,
                size = 50,
            )
        }
    }

    @Test
    fun `KakaoBlogSearchRequest에서 page range를 초과한 경우 예외가 발생`() {
        // when
        val result = assertThrows<AssertionError> {
            KakaoBlogSearchRequest(
                query = "떡볶이",
                sort = KakaoBlogSearchRequest.SortingOption.ACCURACY_FIRST,
                page = 0,
                size = 50,
            )
        }

        // then
        Assertions.assertThat(result.message).isEqualTo(KakaoBlogSearchRequest.PAGE_OUT_OF_RANGE_MESSAGE)
    }

    @Test
    fun `KakaoBlogSearchRequest에서 start range가 초과한 경우 예외가 발생`() {
        // when
        val result = assertThrows<AssertionError> {
            KakaoBlogSearchRequest(
                query = "떡볶이",
                sort = KakaoBlogSearchRequest.SortingOption.ACCURACY_FIRST,
                page = 10,
                size = 55,
            )
        }

        // then
        Assertions.assertThat(result.message).isEqualTo(KakaoBlogSearchRequest.SIZE_OUT_OF_RANGE_MESSAGE)
    }
}
