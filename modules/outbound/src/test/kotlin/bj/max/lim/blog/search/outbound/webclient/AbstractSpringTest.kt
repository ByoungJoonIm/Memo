package bj.max.lim.blog.search.outbound.webclient

import bj.max.lim.blog.search.outbound.BlogSearchApplication
import bj.max.lim.blog.search.outbound.webclient.configuration.ClientConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [BlogSearchApplication::class]
)
@ActiveProfiles("test")
@Import(ClientConfiguration::class)
abstract class AbstractSpringTest
