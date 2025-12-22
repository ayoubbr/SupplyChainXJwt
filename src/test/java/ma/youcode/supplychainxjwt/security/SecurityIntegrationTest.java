package ma.youcode.supplychainxjwt.security;

import com.jayway.jsonpath.JsonPath;
import ma.youcode.supplychainxjwt.shared.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setup() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();

        User admin = User.builder()
                .username("admin")
                .email("admin@test.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ADMIN)
                .build();

        User user = User.builder()
                .username("user")
                .email("user@test.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.GESTIONNAIRE_APPROVISIONNEMENT)
                .build();

        userRepository.save(admin);
        userRepository.save(user);
    }

    // Tester le LOGIN (JWT)
    @Test
    void shouldLoginAndReturnJwt() throws Exception {
        String body = """
                {
                  "username": "admin",
                  "password": "password"
                 }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    // Tester accès SANS token
    @Test
    void shouldRejectAccessWithoutToken() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isUnauthorized());
    }

    // Tester accès AVEC token
    private String loginAndGetToken(String username) throws Exception {
        String body = """
                {
                  "username": "%s",
                  "password": "password"
                }
                """.formatted(username);

        MvcResult result = mockMvc
                .perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();

        String response = result.getResponse().getContentAsString();

        return JsonPath.read(response, "$.accessToken");
    }

    // utiliser le token
    @Test
    void shouldAllowAccessWithValidToken() throws Exception {
        String token = loginAndGetToken("user");

        mockMvc.perform(get("/api/customers")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    // Tester les rôles (IMPORTANT)
    @Test
    void shouldRejectUserAccessingAdminEndpoint() throws Exception {
        String token = loginAndGetToken("user");

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    // Tester role GESTIONNAIRE_APPROVISIONNEMENT
    @Test
    void shouldReturnForbidden_whenAdminAccessesGestionnaireEndpoint() throws Exception {
        String token = loginAndGetToken("admin");

        mockMvc.perform(get("/api/customers/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    // Tester token expiré (option avancée)

}
