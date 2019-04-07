package com.riverway.housingfinance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({JwtManager.class})
@PowerMockIgnore("javax.crypto.*")
public class JwtManagerTest {

    private static String USER_ID = "riverway";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private JwtManager jwtManager = new JwtManager();

    @Test
    public void create_and_parse() {
        String jwt = jwtManager.createToken(USER_ID);
        Jws<Claims> claims = jwtManager.parse(jwt);

        assertThat(claims.getHeader().getType()).isEqualTo("JWT");
        assertThat(claims.getHeader().getAlgorithm()).isEqualTo("HS256");
        assertThat(claims.getBody().getIssuer()).isEqualTo("riverway.com");
        assertThat(claims.getBody().get(JwtManager.CLAIM_KEY_USER_ID)).isEqualTo(USER_ID);
    }

    @Test
    public void expiredJwt() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJyaXZlcndheS5jb20iLCJleHAiOjE1NTQ1NzEzOTUsInVzZXJJZCI6InJpdmVyd2F5In0" +
                ".Vk5zUNFSitS_X1Z21TWmklAafITfNajV-GucwF2kwA8";

        thrown.expect(ExpiredJwtException.class);
        jwtManager.parse(jwt);
    }

    @Test
    public void refresh() {
        String token = jwtManager.createToken(USER_ID);
        Date expiration = jwtManager.parse(token).getBody().getExpiration();

        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(expiration.getTime() + 10L);

        String refreshedToken = jwtManager.refresh(token);
        assertThat(token).isNotEqualTo(refreshedToken);
    }

    @Test
    public void refresh_expireTime_갱신() {
        String token = jwtManager.createToken(USER_ID);
        Date expiration = jwtManager.parse(token).getBody().getExpiration();

        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(expiration.getTime() + 10L);

        String refreshedToken = jwtManager.refresh(token);
        Date refreshedExpiration = jwtManager.parse(refreshedToken).getBody().getExpiration();
        assertTrue(refreshedExpiration.after(expiration));
    }
}