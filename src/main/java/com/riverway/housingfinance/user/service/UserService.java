package com.riverway.housingfinance.user.service;

import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.UnAuthenticationException;
import com.riverway.housingfinance.user.domain.User;
import com.riverway.housingfinance.user.domain.UserRepository;
import com.riverway.housingfinance.user.dto.UserDto;
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
            throw new UnAuthenticationException(ErrorMessage.EXIST_ID);
        }
    }

    public UserDto login(String userId, String password) {
        User user = findByUserId(userId);
        user.matchPassword(password, passwordEncoder);
        return user.toUserDto();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.NOT_EXIST_USER));
    }
}
