package bj.max.lim.blog.search.outbound.configuration

import bj.max.lim.blog.search.outbound.database.configuration.JpaConfiguration
import bj.max.lim.blog.search.outbound.webclient.configuration.ClientConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(JpaConfiguration::class, ClientConfiguration::class)
class OutboundConfiguration
