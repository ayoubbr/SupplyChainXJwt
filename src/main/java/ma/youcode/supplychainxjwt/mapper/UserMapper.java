package ma.youcode.supplychainxjwt.mapper;

import ma.youcode.supplychainxjwt.dto.UserRequest;
import ma.youcode.supplychainxjwt.dto.UserResponse;
import ma.youcode.supplychainxjwt.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail().toLowerCase().trim());
        user.setPassword(dto.getPassword().trim());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        return user;
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}

