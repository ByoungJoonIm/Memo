package bj.max.lim.blog.search.outbound.database.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(name = "keyword_rank")
class KeywordRankEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id",
        nullable = false
    )
    var id: Int?,

    @Column(
        name = "keyword",
        nullable = false,
    )
    var keyword: String,

    @Column(
        name = "count",
        nullable = false,
    )
    var count: Long,

    @Column(
        name = "created_at",
        nullable = false
    )
    var createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),

    @Column(
        name = "updated_at",
        nullable = false
    )
    var updatedAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
)
