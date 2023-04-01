package bj.max.lim.blog.search.outbound.webclient.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KakaoBlogSearchProperties::class, NaverBlogSearchProperties::class)
@ComponentScan(
    basePackages = [
        "bj.max.lim.blog.search.outbound.webclient.client",
        "bj.max.lim.blog.search.outbound.webclient.port",
    ]
)
class ClientConfiguration
