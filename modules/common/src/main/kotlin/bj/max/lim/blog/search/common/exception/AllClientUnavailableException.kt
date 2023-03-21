package bj.max.lim.blog.search.common.exception

class AllClientUnavailableException(
    message: String = "현재 이용이 불가능합니다.",
) : RuntimeException(message)
