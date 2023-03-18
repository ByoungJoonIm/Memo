package bj.max.lim.blog.search.outbound.webclient.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("blog.search.naver")
data class NaverBlogSearchProperties(
    val clientId: String,
    val clientSecret: String,
)
