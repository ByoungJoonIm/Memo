package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.AbstractSpringTest
import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KakaoBlogSearchClientTest : AbstractSpringTest() {

    @Autowired
    lateinit var kakaoBlogSearchClient: KakaoBlogSearchClient

    @Test
//    @Disabled("실제 호출 테스트이므로 요청을 보내고 싶으면 disable을 해제하세요.")
    fun `kakao search blog 호출 정상 결과 반환`() {
        // given
        val request = BlogSearchClientRequest(query = "떡볶이")

        // when
        val result = runBlocking {
            kakaoBlogSearchClient.send(request)
        }

        // then
        println(result)
    }
}
