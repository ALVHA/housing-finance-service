package com.riverway.housingfinance.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5, max = 20)
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserDto toUserDto() {
        return new UserDto(id, userId);
    }

    public boolean matchPassword(String inputPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new UnAuthenticationException("비밀번호를 확인해주세요.");
        }
        return true;
    }
}
