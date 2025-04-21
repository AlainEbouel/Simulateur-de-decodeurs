package inf1007.simulateur_decodeur.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "channels")
    @JsonIgnore
    private Set<Decoder> decoders = new HashSet<>();

    public Channel() {
    }

    public Channel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Decoder> getDecoders() {
        return decoders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDecoders(Set<Decoder> decoders) {
        this.decoders = decoders;
    }
}
