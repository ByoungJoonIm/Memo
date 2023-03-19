package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.AbstractSpringTest
import bj.max.lim.blog.search.outbound.webclient.request.NaverBlogSearchRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NaverBlogSearchClientTest : AbstractSpringTest() {

    @Autowired
    lateinit var naverBlogSearchClient: NaverBlogSearchClient

    @Test
//    @Disabled("실제 호출 테스트")
    fun `naver search blog 호출 정상 결과 반환`() {
        // given
        val request = NaverBlogSearchRequest(query = "떡볶이")

        // when
        val result = runBlocking {
            naverBlogSearchClient.send(request).block()
        }

        val result2 = runBlocking {
            naverBlogSearchClient.send(request.copy(start = 2)).block()
        }

        // then
        println(result)
        println(result2)
    }
}
