package ma.youcode.supplychainxjwt.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.supplychainxjwt.dto.UserRequest;
import ma.youcode.supplychainxjwt.dto.UserResponse;
import ma.youcode.supplychainxjwt.model.User;
import ma.youcode.supplychainxjwt.service.UserService;
import ma.youcode.supplychainxjwt.shared.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @RequestParam Role newRole
    ) {
        return ResponseEntity.ok(userService.updateUserRole(id, newRole));
    }

}
