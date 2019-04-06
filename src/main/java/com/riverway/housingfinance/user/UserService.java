package com.riverway.housingfinance.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserDto user) {
        verifyExist(user);
        return userRepository
                .save(user.toEntity(passwordEncoder))
                .toUserDto();
    }

    public void verifyExist(UserDto user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new UnAuthenticationException("이미 존재하는 아이디입니다.");
        }
    }

    public UserDto login(String userId, String password) {
        User user = findByUserId(userId);
        user.matchPassword(password, passwordEncoder);
        return user.toUserDto();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
