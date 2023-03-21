package bj.max.lim.blog.search.domain.configuration

import bj.max.lim.blog.search.outbound.configuration.OutboundConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan(
    basePackages = [
        "bj.max.lim.blog.search.domain.repository",
        "bj.max.lim.blog.search.domain.repository.impl",
        "bj.max.lim.blog.search.domain.service",
    ]
)
@Import(OutboundConfiguration::class)
class DomainConfiguration
