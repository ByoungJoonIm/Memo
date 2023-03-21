#!/bin/sh

# keyword 호출 횟수 증가
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=1\&pageSize=10
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=2\&pageSize=10
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=3\&pageSize=10
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=4\&pageSize=10
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=5\&pageSize=10

curl -X GET http://localhost:8080/BlogSearch?query=banana\&sorting=recency\&page=1\&pageSize=10
curl -X GET http://localhost:8080/BlogSearch?query=banana\&sorting=recency\&page=2\&pageSize=10

clear
curl -X GET http://localhost:8080/KeywordRank
