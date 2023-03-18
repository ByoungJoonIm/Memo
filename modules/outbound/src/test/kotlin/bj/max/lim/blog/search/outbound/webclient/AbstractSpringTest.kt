package bj.max.lim.blog.search.outbound.webclient

import bj.max.lim.blog.search.outbound.BlogSearchApplication
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [BlogSearchApplication::class]
)
abstract class AbstractSpringTest
