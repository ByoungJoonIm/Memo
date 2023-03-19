package bj.max.lim.blog.search.outbound.webclient

import bj.max.lim.blog.search.outbound.BlogSearchTestApplication
import bj.max.lim.blog.search.outbound.database.configuration.JpaEntityConfiguration
import bj.max.lim.blog.search.outbound.webclient.configuration.ClientConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [BlogSearchTestApplication::class]
)
@ActiveProfiles("test")
@Import(ClientConfiguration::class, JpaEntityConfiguration::class)
abstract class AbstractSpringTest
