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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info(
                "Login attempt username={}",
                req.getUsername()
        );

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

        log.info(
                "Login successful userId={} username={}",
                user.getId(),
                user.getUsername()
        );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresIn(900)
                .build();
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        log.info(
                "Token refresh requested refreshToken={}",
                request.getRefreshToken().substring(0, 8) + "..."
        );

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        refreshTokenService.revokeToken(refreshToken);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        log.info(
                "Token refreshed successfully userId={}",
                user.getId()
        );

        return AuthResponse.builder()
                .refreshToken(newRefreshToken.getToken())
                .accessToken(newAccessToken)
                .expiresIn(900)
                .build();
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication) {
        User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

        log.info(
                "Logout requested userId={}",
                user.getId()
        );

        refreshTokenService.revokeUserTokens(user);

        log.info(
                "Logout successful userId={}",
                user.getId()
        );
    }

}

