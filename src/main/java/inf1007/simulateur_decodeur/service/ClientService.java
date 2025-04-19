package inf1007.simulateur_decodeur.service;

import inf1007.simulateur_decodeur.dto.ClientDTO;
import inf1007.simulateur_decodeur.model.Client;
import inf1007.simulateur_decodeur.model.User;
import inf1007.simulateur_decodeur.repository.ClientRepository;
import inf1007.simulateur_decodeur.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        Client savedClient = clientRepository.save(client);

        User user = new User();
        user.setUsername(clientDTO.getName());
        user.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        user.setRole("CLIENT");
        user.setClient(savedClient);

        userRepository.save(user);

        return savedClient;
    }
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

}
