package com.riverway.housingfinance.user.controller;

import com.riverway.housingfinance.bank.controller.BankController;
import com.riverway.housingfinance.security.JwtManager;
import com.riverway.housingfinance.user.dto.UserDto;
import com.riverway.housingfinance.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final JwtManager jwtManager;

    public UserController(UserService userService, JwtManager jwtManager) {
        this.userService = userService;
        this.jwtManager = jwtManager;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        log.debug("회원가입 : {}", userDto);
        UserDto user = userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserDto> login(@RequestBody UserDto loginRequest, HttpServletResponse response) {
        log.debug("로그인 : {}", loginRequest);
        UserDto loginUser = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
        response.setHeader("Authorization", jwtManager.createToken(loginUser.getUserId()));
        return ResponseEntity.ok().body(loginUser);
    }
}
