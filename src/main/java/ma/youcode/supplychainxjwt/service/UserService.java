package ma.youcode.supplychainxjwt.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.UserRequest;
import ma.youcode.supplychainxjwt.dto.UserResponse;
import ma.youcode.supplychainxjwt.mapper.UserMapper;
import ma.youcode.supplychainxjwt.model.User;
import ma.youcode.supplychainxjwt.repository.UserRepository;
import ma.youcode.supplychainxjwt.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // BASIC AUTH AND ASPECT
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail().toLowerCase().trim())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    public UserResponse updateUserRole(Long id, Role newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setRole(newRole);
        User updated = userRepository.save(user);

        return userMapper.toResponse(updated);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    // For JWT
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public String verify(User user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }
}

