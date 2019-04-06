package com.riverway.housingfinance.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private Long id;

    @Size(min = 5, max = 20)
    private String userId;

    @Size(min = 5, max = 20)
    private String password;

    public UserDto(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return userId.equals(userDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return new User(userId, passwordEncoder.encode(password));
    }
}
