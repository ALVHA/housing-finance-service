# housing-finance-service
주택 금융 서비스 API
## 개발환경
* Language - Java 8
* Framework - Spring boot 2.1.3
* Database - H2
* Build Tool - Gradle
## 문제해결 전략
### 데이터 파일을 DB에 저장
* 금융기관 목록을 import.sql 파일에 미리 넣어둠.
* 2016년도 전과 이후 데이터의 형식이 달라서 어려움을 겪음.
  * 2016년도 이전에는 `2246` 이후에는 `"2,246"` 으로 들어옴 - 정규표현식 사용하여 해결
* 2개의 테이블을 만듬
  * 월별 금융기관의 지원합계
  * 년도별 각 금융기관의 지원합계
### Token 기반 API 인증 기능 - JWT
* 로그인을 하면 Authorization 헤더에 token을 생성해서 넣어줌
* JwtTokenInterceptor을 만들고 요청에 대한 인증
  * Authorization 헤더에 `Bearer Token [토큰]`으로 요청이 오면 토큰 유효성을 확인하고 재발급한다.

