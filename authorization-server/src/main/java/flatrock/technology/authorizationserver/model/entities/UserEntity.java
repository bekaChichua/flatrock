package flatrock.technology.authorizationserver.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        CLIENT,
        SELLER,
        ADMIN
    }
}
