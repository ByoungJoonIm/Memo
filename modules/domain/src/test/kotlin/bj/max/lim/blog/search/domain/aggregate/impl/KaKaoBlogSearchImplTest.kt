package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.KakaoBlogSearchResponse
import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import bj.max.lim.blog.search.outbound.webclient.response.Meta
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId

class KaKaoBlogSearchImplTest {

    @Test
    fun `KakaoBlogSearchImpl test`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

        val kakaoBlogSearchResponse = KakaoBlogSearchResponse(
            meta = Meta(
                totalCount = 100,
                pageableCount = 2,
                isEnd = false
            ),
            documents = listOf(
                KakaoDocument(
                    title = "떡볶이 사랑",
                    contents = "떡볶이는 너무 맛있어요. 치킨이랑 같이 먹어도 너무 맛있답니다.",
                    url = "https://blog.kakao.com/13451515",
                    blogName = "떡볶이 마녀",
                    thumbnail = "https://blog.kakao.com/thumbnail/13451515",
                    datetime = now,
                ),
                KakaoDocument(
                    title = "떡볶이 love",
                    contents = "떡볶이는 너무 맛있어요. 치킨이랑 같이 먹어도 너무 맛있답니다.",
                    url = "https://blog.kakao.com/13451516",
                    blogName = "떡볶이 lover",
                    thumbnail = "https://blog.kakao.com/thumbnail/13451516",
                    datetime = now,
                ),
            )
        )

        // when
        val result = KakaoBlogSearchImpl(kakaoBlogSearchResponse)

        // then
        assertThat(result.total).isEqualTo(kakaoBlogSearchResponse.meta.totalCount)
        assertThat(result.blogList).hasSize(2)
    }
}
