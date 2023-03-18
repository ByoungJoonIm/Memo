package bj.max.lim.blog.search.outbound.webclient.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KakaoBlogSearchProperties::class, NaverBlogSearchProperties::class)
class ClientConfiguration
