package inf1007.simulateur_decodeur.controller;

import inf1007.simulateur_decodeur.dto.DecoderDTO;
import inf1007.simulateur_decodeur.model.Channel;
import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.model.User;
import inf1007.simulateur_decodeur.service.ChannelService;
import inf1007.simulateur_decodeur.service.DecoderActionService;
import inf1007.simulateur_decodeur.service.DecoderService;
import inf1007.simulateur_decodeur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private DecoderService decoderService;

    @Autowired
    private DecoderActionService decoderActionService;

    @Autowired
    private ChannelService channelService;

    @GetMapping("/decoders")
    public ResponseEntity<List<DecoderDTO>> getClientDecoders(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        List<DecoderDTO> decoders = decoderService.findByClient(user.getClient());
        return ResponseEntity.ok(decoders);
    }

    @GetMapping("/decoder/{id}/status")
    public ResponseEntity<String> getDecoderStatus(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Decoder decoder = decoderService.findById(id);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String status = decoderActionService.getStatus(decoder.getIpAddress());
        return ResponseEntity.ok(status);
    }

    @PostMapping("/decoder/{id}/reinit")
    public ResponseEntity<String> reinitDecoder(@PathVariable Long id, Authentication authentication) {
        return handleDecoderAction(id, authentication, decoderActionService::reinit);
    }

    @PostMapping("/decoder/{id}/reboot")
    public ResponseEntity<String> rebootDecoder(@PathVariable Long id, Authentication authentication) {
        return handleDecoderAction(id, authentication, decoderActionService::reboot);
    }

    @PostMapping("/decoder/{id}/shutdown")
    public ResponseEntity<String> shutdownDecoder(@PathVariable Long id, Authentication authentication) {
        return handleDecoderAction(id, authentication, decoderActionService::shutdown);
    }

    private ResponseEntity<String> handleDecoderAction(Long id, Authentication authentication,
                                                       java.util.function.Function<String, String> action) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Decoder decoder = decoderService.findById(id);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String response = action.apply(decoder.getIpAddress());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/channels")
    public ResponseEntity<List<Channel>> listAllChannels() {
        return ResponseEntity.ok(channelService.getAllChannels());
    }

    @PostMapping("/decoder/{decoderId}/add-channel/{channelId}")
    public ResponseEntity<String> addChannelToDecoder(@PathVariable Long decoderId,
                                                      @PathVariable Long channelId,
                                                      Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Decoder decoder = decoderService.findById(decoderId);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Channel channel = channelService.findById(channelId);
        decoder.getChannels().add(channel);
        decoderService.save(decoder);

        return ResponseEntity.ok("{\"response\": \"Channel added\"}");
    }

    @DeleteMapping("/decoder/{decoderId}/remove-channel/{channelId}")
    public ResponseEntity<String> removeChannelFromDecoder(@PathVariable Long decoderId,
                                                           @PathVariable Long channelId,
                                                           Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Decoder decoder = decoderService.findById(decoderId);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Channel channel = channelService.findById(channelId);
        decoder.getChannels().remove(channel);
        decoderService.save(decoder);

        return ResponseEntity.ok("{\"response\": \"Channel removed\"}");
    }
    @GetMapping("/decoder/{id}/channels")
    public ResponseEntity<List<Channel>> getChannelsByDecoder(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        Decoder decoder = decoderService.findById(id);

        if (!decoder.getClient().getId().equals(user.getClient().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(channelService.getChannelsByDecoderId(id));
    }

}
