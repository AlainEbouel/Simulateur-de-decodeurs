package inf1007.simulateur_decodeur.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    private String password;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Decoder> decoders;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Decoder> getDecoders() {
        return decoders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDecoders(List<Decoder> decoders) {
        this.decoders = decoders;
    }
}
