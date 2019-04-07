package com.riverway.housingfinance.security;

import com.riverway.housingfinance.bank.controller.BankController;
import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(JwtTokenInterceptor.class);
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
