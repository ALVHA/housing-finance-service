package com.riverway.housingfinance.security;

import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_AUTH = "Authorization";

    private final JwtManager jwtManager;

    public JwtTokenInterceptor(JwtManager jwtManager) {
        this.jwtManager = jwtManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String token = request.getHeader(HEADER_AUTH);
        log.debug("Jwt Token : {}", token);
        if (token == null) {
            throw new JwtAuthorizationException(ErrorMessage.NOT_EXIST_TOKEN);
        }

        if (token.startsWith("Bearer Token")) {
            String target = token.substring("Bearer Token".length()).trim();
            String refreshToken = jwtManager.refresh(target);

            response.setHeader(HEADER_AUTH, refreshToken);
            return true;
        }

        jwtManager.parse(token);
        return true;
    }
}
