package inf1007.simulateur_decodeur.service;

import inf1007.simulateur_decodeur.dto.DecoderDTO;
import inf1007.simulateur_decodeur.model.Client;
import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.model.User;
import inf1007.simulateur_decodeur.repository.DecoderRepository;
import inf1007.simulateur_decodeur.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecoderService {

    private final DecoderRepository decoderRepository;
    private final UserRepository userRepository;

    public DecoderService(DecoderRepository decoderRepository, UserRepository userRepository) {
        this.decoderRepository = decoderRepository;
        this.userRepository = userRepository;
    }

    public List<Decoder> getDecodersForCurrentClient(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//        return decoderRepository.findByClient(user.getClient());
        User user = userRepository.findByUsername(username).orElseThrow();
        Long clientId = user.getClient().getId();
        return decoderRepository.findByClientId(clientId);
    }

    public Decoder assignDecoder(Decoder decoder) {
        if (decoderRepository.existsByIpAddress(decoder.getIpAddress())) {
            throw new RuntimeException("Already used IP address.");
        }
        return decoderRepository.save(decoder);
    }

    public void removeDecoder(Long id) {
        decoderRepository.deleteById(id);
    }
    public Decoder findById(Long id) {
        return decoderRepository.findById(id).orElseThrow(() -> new RuntimeException("Decoder not found"));
    }
    public void deleteDecoder(Long id) {
        decoderRepository.deleteById(id);
    }

    public List<DecoderDTO> findByClient(Client client) {
        return decoderRepository.findByClient(client).stream()
                .map(decoder -> new DecoderDTO(decoder.getId(), decoder.getIpAddress()))
                .toList();
    }

}
