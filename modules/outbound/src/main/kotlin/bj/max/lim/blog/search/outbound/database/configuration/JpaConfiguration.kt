package bj.max.lim.blog.search.outbound.database.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(
    basePackages = [
        "bj.max.lim.blog.search.outbound.database.model",
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "bj.max.lim.blog.search.outbound.database.repository",
    ]
)
class JpaConfiguration
