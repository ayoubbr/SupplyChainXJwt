package ma.youcode.supplychainxjwt.service;

import jakarta.persistence.EntityNotFoundException;
import ma.youcode.supplychainxjwt.security.UserRequest;
import ma.youcode.supplychainxjwt.security.UserResponse;
import ma.youcode.supplychainxjwt.security.UserMapper;
import ma.youcode.supplychainxjwt.security.User;
import ma.youcode.supplychainxjwt.security.UserRepository;
import ma.youcode.supplychainxjwt.security.JWTService;
import ma.youcode.supplychainxjwt.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    @Autowired
    public PasswordEncoder encoder;


    public UserResponse createUser(UserRequest request) {
        log.info(
                "User creation requested username={} role={}",
                request.getUsername(),
                request.getRole()
        );


        if (userRepository.existsByEmail(request.getEmail().toLowerCase().trim())) {
            log.warn(
                    "User creation failed: email already exists email={}",
                    request.getEmail()
            );

            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername().toLowerCase().trim())) {
            log.warn(
                    "User creation failed: username already exists username={}",
                    request.getUsername()
            );

            throw new IllegalArgumentException("Username already exists");
        }

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(user);

        log.info(
                "User created successfully userId={} username={} role={}",
                saved.getId(),
                saved.getUsername(),
                saved.getRole()
        );

        return userMapper.toResponse(saved);
    }

    public UserResponse updateUserRole(Long id, Role newRole) {
        log.info(
                "User role update requested userId={} newRole={}",
                id,
                newRole
        );

        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            log.error(
                    "User role update failed: user not found userId={}",
                    id
            );
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        User user = userOpt.get();

        user.setRole(newRole);
        User updated = userRepository.save(user);

        log.info(
                "User role updated successfully userId={} role={}",
                updated.getId(),
                updated.getRole()
        );

        return userMapper.toResponse(updated);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

}

