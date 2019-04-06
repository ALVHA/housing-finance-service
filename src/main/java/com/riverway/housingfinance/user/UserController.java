package com.riverway.housingfinance.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto){
        log.debug("회원가입 : {}", userDto);
        UserDto user = userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserDto> login(@RequestBody UserDto loginRequest){
        log.debug("로그인 : {}", loginRequest);
        UserDto user = userService.login(loginRequest.getUserId(), loginRequest.getPassword());
        return ResponseEntity.ok().body(user);
    }
}
