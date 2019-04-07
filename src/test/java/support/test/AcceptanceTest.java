package support.test;

import com.riverway.housingfinance.security.JwtManager;
import com.riverway.housingfinance.user.UserDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    private static String DEFAULT_USER_ID = "riverway";

    @Autowired
    private TestRestTemplate template;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JwtManager jwtManager;

    public TestRestTemplate template() {
        return template;
    }

    protected String createJwt(String userId) {
        return jwtManager.createToken(userId);
    }

    protected <T> HttpEntity<T> jwtEntity(Object object) {
        return new HttpEntity<>((T) object, jwtHeaders(DEFAULT_USER_ID));
    }

    protected HttpEntity jwtEntity() {
        return new HttpEntity(jwtHeaders(DEFAULT_USER_ID));
    }

    protected <T> ResponseEntity<T> requestPost(String url, HttpEntity httpEntity, Class<T> responseType) {
        return template.exchange(url, HttpMethod.POST, httpEntity, responseType);
    }

    protected <T> ResponseEntity<T> requestGet(String url, HttpEntity httpEntity, Class<T> responseType) {
        return template.exchange(url, HttpMethod.GET, httpEntity, responseType);
    }

    protected HttpHeaders jwtHeaders(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", createJwt(userId));
        return headers;
    }

    protected HttpEntity<MultiValueMap<String, Object>> uploadCsvFileRequest() {
        ClassPathResource csvFile = new ClassPathResource("주택금융신용보증_금융기관별_공급현황.csv");
        return HtmlFormDataBuilder
                .multipartFormDataWithToken(createJwt(DEFAULT_USER_ID))
                .addParameter("csvFile", csvFile)
                .build();
    }

    protected void registerData() {
        HttpEntity<MultiValueMap<String, Object>> request = uploadCsvFileRequest();
        ResponseEntity<String> response = template().postForEntity("/api/housing/finance", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    protected UserDto createUserDefault() {
        return new UserDto(DEFAULT_USER_ID, "password");
    }

    protected UserDto createUser(String userId) {
        return new UserDto(userId, "password");
    }

    protected UserDto registerUser(String userId) {
        UserDto user = createUser(userId);
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", user, UserDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return user;
    }
}
