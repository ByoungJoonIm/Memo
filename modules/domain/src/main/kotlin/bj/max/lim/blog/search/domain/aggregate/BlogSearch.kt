package bj.max.lim.blog.search.domain.aggregate

interface BlogSearch {
    // 검색된 결과 갯수
    val total: Int

    // 검색 결과
    val blogList: List<Blog>
}
