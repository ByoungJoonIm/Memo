package bj.max.lim.blog.search.outbound.webclient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("blog.search.kakao")
data class KakaoBlogSearchProperties(
    val apiKey: String,
)
