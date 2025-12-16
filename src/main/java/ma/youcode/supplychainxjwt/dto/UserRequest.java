package ma.youcode.supplychainxjwt.dto;

import lombok.*;
import ma.youcode.supplychainxjwt.shared.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}