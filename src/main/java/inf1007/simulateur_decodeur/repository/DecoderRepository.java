package inf1007.simulateur_decodeur.repository;

import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecoderRepository extends JpaRepository<Decoder, Long> {
    List<Decoder> findByClient(Client client);
    List<Decoder> findByClientId(Long clientId);
    boolean existsByIpAddress(String ipAddress);

}
