package ma.youcode.supplychainxjwt.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;

    private String refreshToken;

    private long expiresIn;
}
