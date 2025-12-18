package ma.youcode.supplychainxjwt.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        User user = ((UserPrincipal) userDetails).getUser();
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresIn(900)
                .build();
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        refreshTokenService.revokeToken(refreshToken);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .refreshToken(newRefreshToken.getToken())
                .accessToken(newAccessToken)
                .expiresIn(900)
                .build();
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();
        refreshTokenService.revokeUserTokens(user);
    }

}

