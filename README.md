# Blog Search

## Download jar
- [https://drive.google.com/drive/folders/1n_92LnQ4_PSb3778jeNTKoiTNDKPQkkV](https://drive.google.com/drive/folders/1n_92LnQ4_PSb3778jeNTKoiTNDKPQkkV)
  - 다운로드를 누르면 반응이 없다가, 약 10초 후에 `바이러스 검사가 불가능` 메세지가 출력되고, 다운로드가 가능합니다.

## Spec
### BlogSearch
- `http://localhost:8080/BlogSearch`
  - parameters
    - `query` : 검색어
    - `page` : 검색 시작 페이지 위치 (1~50) default 1
    - `pageSize` : 페이지 사이즈(1~20) default 10
    - `sorting` : 정렬 방법. 정확도 혹은 최신순 ("accuracy", "recency")
  - response
    - `total` : 검색된 결과 갯수
    - `blogList` : 검색 결과
      - Blog
        - `title` : 블로그 글 제목
        - `blogName` : 블로그 이름
        - `url` : 블로그 글 url
        - `description` : 블로그 글 요약
        - `postDate` : 블로그 글 작성 날짜
  - sample curl
    ```shell
    curl -X GET http://localhost:8080/BlogSearch?query=apple\&sorting=recency\&page=1\&pageSize=10
    ```
    - `script` directory에 샘플 스크립트가 있습니다.

### KeywordRank
- `http://localhost:8080/KeywordRank`
  - response
    - `keywordRankList` : 키워드에 따른 검색 횟수 (최대 10개)
      - KeywordRank
        - `keyword` : 검색한 키워드
        - `count` : 키워드 호출 횟수
  - sample curl
    ```shell
    curl -X GET http://localhost:8080/KeywordRank
    ```
  - `script` directory에 샘플 스크립트가 있습니다.

## Modules
- 호출 순서
  - server -> domain -> outbound -> domain -> server

## Test Tip
### script
script 디렉터리에 바로 실행 가능한 스크립트가 준비되어 있습니다.

### jar
- `java -jar jar/server-0.0.1-SNAPSHOT.jar`

### gradlew로 테스트 / 빌드 실행
- `./gradlew test`
- `./gradlew build`
