package ma.youcode.supplychainxjwt.model;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.supplychainxjwt.shared.enums.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

}

