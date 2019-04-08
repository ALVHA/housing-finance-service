# housing-finance-service
주택 금융 서비스 API
## 문제해결 전략
### 데이터 파일을 DB에 저장
#### csv파일 파싱
* 2016년도 전과 이후 데이터의 형식이 달라서 어려움을 겪음.
  * 2016년도 이전에는 `2246` 2016년도 이후에는 `"2,246"` 으로 들어옴
  * 패턴 매칭(Pattern Matching)을 사용하여 해결
#### 엔티티 설계
* 처음에는 `년/월/은행/금액`으로 엔티티 설계를 고려
  * `년/은행` 컬럼의 데이터 중복과 많은 데이터로 성능개선이 필요
  * 년도별 금융기관의 지원금액을 사용하는 API가 많아 코드 복잡도 증가와 쿼리가 복잡해짐 
*  `년도별 금융기관 지원금액`엔티티를 만들고 `월별 금융기관 지원금액`과 일대다 관계로 매핑
*  데이터를 파싱하고 저장하는데 2200ms 정도가 걸렸지만 변경 후 770ms 정도로 성능 개선
- [해당 이슈](https://github.com/KangGilHwan/housing-finance-service/issues/1)
### 주택 금융기관 목록 출력하는 API
* 금융기관 목록을 import.sql 파일에 미리 넣어둠
* Primary Key는 `institute_code` 
* 금융기관 전체 목록을 가져와 JSON 형태로 응답
### 년도별 각 금융기관의 지원금액 합계를 출력하는 API
* `년도별 금융기관 지원금액` 테이블에서 데이터를 읽어와 합계를 구한 후 JSON 형태로 응답
### 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
* `년도별 금융기관 지원금액` 테이블에서 데이터를 읽어와 가장 큰 금액을 구한 후 해당 데이터를 읽어와 JSON 형태로 응답
### 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
*  `년도별 금융기관 지원금액` 테이블에서 가장 작은 금액과 큰 금액에 해당하는 데이터를 읽어와 JSON 형태로 응답
*  `/api/housing/finance/{fileId}/banks/{bankName}/years/amount`과 같은 방식으로 은행의 이름을 url에 넣어 은행별로 해당기능 수행가능 
### Token 기반 API 인증 기능 - JWT
* Java JWT 라이브러리인 jjwt를 사용
* 로그인후에 JWT Token을 생성해서 Authorization 헤더에 토큰 발급
* JwtTokenInterceptor에서 API 호출시 인증 진행
  * Authorization 헤더에 `Bearer Token [토큰]`으로 요청이 오면 토큰 유효성을 확인하고 재발급한다.
### 개발환경
* Language - Java 8
* Framework - Spring boot 2.1.3
* Database - H2
* Build Tool - Gradle
### 빌드 및 실행 방법
1. GIt 저장소를 다운로드 혹은 clone
2. gradlew 파일이 있는 경로에서 ./gradlew build 로 빌드
3. java -jar build/libs/housingfinance-0.0.1-SNAPSHOT.jar 실행
4. http://localhost:8080/h2-console 접속해 내용 확인
5. JDBC : jdbc:h2:~/housingfinance
### 테이블 구조 
![housingfinance](https://user-images.githubusercontent.com/36291553/55689707-df83e000-59c2-11e9-8f31-22b05d4296c1.png)
