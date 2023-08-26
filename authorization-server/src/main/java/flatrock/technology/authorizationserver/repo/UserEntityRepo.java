package flatrock.technology.authorizationserver.repo;

import flatrock.technology.authorizationserver.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    void deleteUserEntityByEmail(String email);
}
