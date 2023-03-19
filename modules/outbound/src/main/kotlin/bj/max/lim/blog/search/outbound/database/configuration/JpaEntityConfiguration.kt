package bj.max.lim.blog.search.outbound.database.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan(
    basePackages = [
        "bj.max.lim.blog.search.outbound.database.model"
    ]
)
class JpaEntityConfiguration
