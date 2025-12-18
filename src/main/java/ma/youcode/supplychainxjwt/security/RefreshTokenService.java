package ma.youcode.supplychainxjwt.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_DURATION_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days

    public RefreshToken createRefreshToken(User user) {
        //  one refresh token per user
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_DURATION_MS))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found."));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked.");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired.");
        }

        return refreshToken;
    }

    public void revokeToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public void revokeUserTokens(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

}
