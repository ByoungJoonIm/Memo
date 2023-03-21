#!/bin/sh

# jq가 없다면
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=1\&pageSize=10

# jq가 있다면
curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=1\&pageSize=10 | jq
