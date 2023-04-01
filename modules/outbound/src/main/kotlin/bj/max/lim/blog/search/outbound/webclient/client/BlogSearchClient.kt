package bj.max.lim.blog.search.outbound.webclient.client

import bj.max.lim.blog.search.outbound.webclient.request.BlogSearchClientRequest
import bj.max.lim.blog.search.outbound.webclient.response.BlogSearchClientResponse

interface BlogSearchClient {
    suspend fun send(request: BlogSearchClientRequest): BlogSearchClientResponse
}
