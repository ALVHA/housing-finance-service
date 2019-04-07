package com.riverway.housingfinance.security;

import com.riverway.housingfinance.support.exception.CannotGenerateJwtKeyException;
import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public class JwtManager {

    private static final String ISSUER = "riverway.com";
    private static final String SECRET = "Sea is salt";
    public static final String CLAIM_KEY_USER_ID = "userId";

    private static final int HOUR = 60 * 60 * 1000;
    private final int expirationTime = 2 * HOUR;

    public String createToken(String userId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", HS256)
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim(CLAIM_KEY_USER_ID, userId)
                .signWith(HS256, generateKey())
                .compact();
    }

    private byte[] generateKey() {
        try {
            byte[] key = SECRET.getBytes("UTF-8");
            return key;
        } catch (UnsupportedEncodingException e) {
            throw new CannotGenerateJwtKeyException(ErrorMessage.GENERATE_JWT_KEY);
        }
    }

    public String decode(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        try {
            Jws<Claims> claims = parse(jwt);
            return claims.getBody().get(CLAIM_KEY_USER_ID).toString();
        } catch (Exception e) {
            throw new JwtAuthorizationException(ErrorMessage.INVALID_TOKEN);
        }
    }

    public String decode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return decode(request);
    }

    public Jws<Claims> parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(jwt);
    }

    public String refresh(String jwt) {
        String userId = parseUserId(jwt);
        return createToken(userId);
    }

    public String parseUserId(String jwt) {
        return parse(jwt)
                .getBody()
                .get(CLAIM_KEY_USER_ID)
                .toString();
    }
}
