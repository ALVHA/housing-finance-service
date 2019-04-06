package support.test;

import com.riverway.housingfinance.user.UserDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @Autowired
    private TestRestTemplate template;

    public TestRestTemplate template() {
        return template;
    }

    protected HttpEntity<MultiValueMap<String, Object>> uploadCsvFileRequest() {
        ClassPathResource csvFile = new ClassPathResource("주택금융신용보증_금융기관별_공급현황.csv");
        return HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("csvFile", csvFile)
                .build();
    }

    protected void registerData() {
        HttpEntity<MultiValueMap<String, Object>> request = uploadCsvFileRequest();
        ResponseEntity<String> response = template().postForEntity("/api/housing/finance", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    protected UserDto createUserDefault() {
        return new UserDto("riverway", "password");
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
