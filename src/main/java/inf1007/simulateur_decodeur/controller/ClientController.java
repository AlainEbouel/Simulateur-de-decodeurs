package inf1007.simulateur_decodeur.controller;

import inf1007.simulateur_decodeur.dto.DecoderDTO;
import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.model.User;
import inf1007.simulateur_decodeur.service.DecoderActionService;
import inf1007.simulateur_decodeur.service.DecoderService;
import inf1007.simulateur_decodeur.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final UserService userService;
    private final DecoderService decoderService;
    private final DecoderActionService decoderActionService;

    public ClientController(UserService userService,
                            DecoderService decoderService,
                            DecoderActionService decoderActionService) {
        this.userService = userService;
        this.decoderService = decoderService;
        this.decoderActionService = decoderActionService;
    }

    @GetMapping("/decoders")
    public List<DecoderDTO> getClientDecoders(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return decoderService.findByClient(user.getClient());
    }

    @GetMapping("/decoder/{id}/status")
    public ResponseEntity<String> getDecoderStatus(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Decoder decoder = decoderService.findById(id);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String status = decoderActionService.getStatus(decoder.getIpAddress());
        return ResponseEntity.ok(status);
    }
}
