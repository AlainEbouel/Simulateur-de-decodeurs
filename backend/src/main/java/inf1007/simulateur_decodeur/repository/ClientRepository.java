package inf1007.simulateur_decodeur.repository;

import inf1007.simulateur_decodeur.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
