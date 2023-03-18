package bj.max.lim.blog.search.outbound.webclient

interface BlogSearch {
    fun search() // TODO

    enum class SortingOptions {
        ACCURACY_FIRST, // 정확도순
        RECENCY_FIRST, // 최신순
    }
}
