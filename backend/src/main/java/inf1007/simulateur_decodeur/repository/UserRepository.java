package inf1007.simulateur_decodeur.repository;

import inf1007.simulateur_decodeur.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
