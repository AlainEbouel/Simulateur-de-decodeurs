package inf1007.simulateur_decodeur.controller;

import inf1007.simulateur_decodeur.dto.ClientDTO;
import inf1007.simulateur_decodeur.model.Client;
import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.model.User;
import inf1007.simulateur_decodeur.repository.DecoderRepository;
import inf1007.simulateur_decodeur.service.ClientService;
import inf1007.simulateur_decodeur.service.DecoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DecoderService decoderService;

    private static final List<String> ALL_IPS = List.of(
             "127.0.10.1", "127.0.10.2", "127.0.10.3",
            "127.0.10.4", "127.0.10.5", "127.0.10.6", "127.0.10.7",
            "127.0.10.8", "127.0.10.9", "127.0.10.10", "127.0.10.11",
            "127.0.10.12"
    );

    private final DecoderRepository decoderRepository;
        public AdminController(DecoderRepository decoderRepository) {
        this.decoderRepository = decoderRepository;
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.createClient(clientDTO);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }


    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/decoders")
    public ResponseEntity<List<Decoder>> getAllDecoders() {
        return ResponseEntity.ok(decoderService.getAllDecoders());
    }

    @PostMapping("/decoders")
    public ResponseEntity<?> assignDecoder(@RequestBody Decoder decoder) {
        if (!isValidIp(decoder.getIpAddress())) {
            return ResponseEntity.badRequest().body("Invalid IP address. Must be in 127.0.10.1 to 127.0.10.12");
        }
        decoderService.save(decoder);
        return ResponseEntity.ok("Decoder assigned successfully");
    }

    @DeleteMapping("/decoders/{id}")
    public ResponseEntity<Void> deleteDecoder(@PathVariable Long id) {
        decoderService.deleteDecoder(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isValidIp(String ip) {
        String prefix = "127.0.10.";
        if (!ip.startsWith(prefix)) return false;
        try {
            int lastOctet = Integer.parseInt(ip.substring(prefix.length()));
            return lastOctet >= 1 && lastOctet <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @GetMapping("/decoders/unassigned")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<String>> getUnassignedDecoders() {
        List<String> assignedIps = decoderRepository.findAll()
                .stream()
                .map(Decoder::getIpAddress)
                .toList();

        List<String> availableIps = ALL_IPS.stream()
                .filter(ip -> !assignedIps.contains(ip))
                .toList();

        return ResponseEntity.ok(availableIps);
    }
}
