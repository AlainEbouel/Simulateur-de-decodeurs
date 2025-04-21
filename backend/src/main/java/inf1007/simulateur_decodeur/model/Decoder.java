package inf1007.simulateur_decodeur.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Decoder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
    @ManyToMany
    @JoinTable(
            name = "decoder_channel",
            joinColumns = @JoinColumn(name = "decoder_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    private Set<Channel> channels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Client getClient() {
        return client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

}
