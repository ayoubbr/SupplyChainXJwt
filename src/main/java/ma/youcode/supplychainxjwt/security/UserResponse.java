package ma.youcode.supplychainxjwt.security;


import lombok.*;
import ma.youcode.supplychainxjwt.shared.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String username;
}