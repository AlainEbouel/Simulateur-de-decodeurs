package inf1007.simulateur_decodeur.repository;

import inf1007.simulateur_decodeur.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT c FROM Channel c JOIN c.decoders d WHERE d.id = :decoderId")
    List<Channel> findByDecoderId(@Param("decoderId") Long decoderId);
}
