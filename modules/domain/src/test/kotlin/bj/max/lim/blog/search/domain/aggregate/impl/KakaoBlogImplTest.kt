package bj.max.lim.blog.search.domain.aggregate.impl

import bj.max.lim.blog.search.outbound.webclient.response.KakaoDocument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class KakaoBlogImplTest {

    @Test
    fun `KakaoBlogImpl test`() {
        // given
        val now = LocalDateTime.parse("2023-03-19T18:00:00").atZone(ZoneId.of("Asia/Seoul")).toInstant()

        val kakaoDocument = KakaoDocument(
            title = "떡볶이 사랑",
            contents = "떡볶이는 너무 맛있어요. 치킨이랑 같이 먹어도 너무 맛있답니다.",
            url = "https://blog.kakao.com/13451515",
            blogName = "떡볶이 마녀",
            thumbnail = "https://blog.kakao.com/thumbnail/13451515",
            datetime = now,
        )

        // when
        val result = KakaoBlogImpl(kakaoDocument)

        // then
        assertThat(result.title).isEqualTo(kakaoDocument.title)
        assertThat(result.description).isEqualTo(kakaoDocument.contents)
        assertThat(result.url).isEqualTo(kakaoDocument.url)
        assertThat(result.blogName).isEqualTo(kakaoDocument.blogName)
        assertThat(result.postDate).isEqualTo(LocalDate.ofInstant(kakaoDocument.datetime, ZoneId.of("Asia/Seoul")))
    }
}
