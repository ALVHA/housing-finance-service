package com.riverway.housingfinance.security;

import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtInterceptorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private JwtTokenInterceptor jwtTokenInterceptor;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;

    @Before
    public void setUp() {
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        request.setRequestURI("/test");
        request.setMethod("GET");
    }

    @Test
    public void prehandle_exist_token() {
        String token = "test";
        request.addHeader("Authorization", token);

        when(jwtManager.parse(token)).thenReturn(any());

        assertTrue(jwtTokenInterceptor.preHandle(request, response, null));
    }

    @Test
    public void refreshToken_check() {
        String token = "test";
        String refreshRequest = "Bearer Token " + token;
        request.addHeader("Authorization", refreshRequest);

        when(jwtManager.refresh(token)).thenReturn("another");

        assertTrue(jwtTokenInterceptor.preHandle(request, response, null));
        assertThat(response.getHeader("Authorization")).isNotEqualTo(token);
        assertThat(response.getHeader("Authorization")).isEqualTo("another");
    }

    @Test
    public void prehandle_no_token() {
        thrown.expect(JwtAuthorizationException.class);
        thrown.expectMessage(ErrorMessage.NOT_EXIST_TOKEN);

        jwtTokenInterceptor.preHandle(request, response, null);
    }
}
