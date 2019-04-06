package com.riverway.housingfinance.security;

import com.riverway.housingfinance.support.exception.CannotGenrateJwtKeyException;
import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import com.riverway.housingfinance.user.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Slf4j
@Component("jwtManager")
public class JwtManager {

    private static final String ISSUER = "riverway.com";
    private static final String SALT = "Sea is salt";
    private static final String CLAIM_KEY_USER_ID = "userId";

    private static final int HOUR = 60 * 60 * 1000;
    private final int expirationTime = 2 * HOUR;

    public String createToken(UserDto user) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", HS256)
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim(CLAIM_KEY_USER_ID, user.getUserId())
                .signWith(HS256, generateKey())
                .compact();
    }

    private byte[] generateKey() {
        try {
            byte[] key = SALT.getBytes("UTF-8");
            return key;
        } catch (UnsupportedEncodingException e) {
            throw new CannotGenrateJwtKeyException(ErrorMessage.GENERATE_JWT_KEY);
        }
    }

    public String decode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        try {
            Jws<Claims> claims = parse(jwt);
            return claims.getBody().get(CLAIM_KEY_USER_ID).toString();
        } catch (Exception e) {
            throw new JwtAuthorizationException(ErrorMessage.INVALID_TOKEN);
        }
    }

    public Jws<Claims> parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(jwt);
    }
}
