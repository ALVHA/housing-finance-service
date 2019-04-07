package com.riverway.housingfinance.security;

import com.riverway.housingfinance.bank.controller.BankController;
import com.riverway.housingfinance.support.exception.CannotGenerateJwtKeyException;
import com.riverway.housingfinance.support.exception.FailedReadCsvFileException;
import com.riverway.housingfinance.support.exception.JwtAuthorizationException;
import com.riverway.housingfinance.support.exception.UnAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class SecurityRestControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(BankController.class);

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> unAuthentication(UnAuthenticationException e) {
        log.debug("UnAuthenticationException occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(JwtAuthorizationException.class)
    public ResponseEntity<Void> jwtAuthorization(JwtAuthorizationException e) {
        log.debug("JwtAuthorizationException occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler({CannotGenerateJwtKeyException.class, FailedReadCsvFileException.class})
    public ResponseEntity<Void> internalServer(CannotGenerateJwtKeyException e) {
        log.debug("500 Error occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> entityNotFound(EntityNotFoundException e) {
        log.debug("EntityNotFoundException occur! : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
