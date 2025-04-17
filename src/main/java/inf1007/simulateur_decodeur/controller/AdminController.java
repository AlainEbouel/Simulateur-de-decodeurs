package inf1007.simulateur_decodeur.controller;

import inf1007.simulateur_decodeur.model.Client;
import inf1007.simulateur_decodeur.model.Decoder;
import inf1007.simulateur_decodeur.service.ClientService;
import inf1007.simulateur_decodeur.service.DecoderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ClientService clientService;
    private final DecoderService decoderService;

    public AdminController(ClientService clientService, DecoderService decoderService) {
        this.clientService = clientService;
        this.decoderService = decoderService;
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/decoders")
    public Decoder assignDecoder(@RequestBody Decoder decoder) {
        return decoderService.assignDecoder(decoder);
    }

    @DeleteMapping("/decoders/{id}")
    public ResponseEntity<Void> deleteDecoder(@PathVariable Long id) {
        decoderService.deleteDecoder(id);
        return ResponseEntity.noContent().build();
    }
}
