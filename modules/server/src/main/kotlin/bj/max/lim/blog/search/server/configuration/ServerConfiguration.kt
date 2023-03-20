package bj.max.lim.blog.search.server.configuration

import bj.max.lim.blog.search.domain.configuration.DomainConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DomainConfiguration::class)
class ServerConfiguration
