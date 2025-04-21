package inf1007.simulateur_decodeur.dto;

public class DecoderDTO {
    private Long id;
    private String ipAddress;

    public DecoderDTO(Long id, String ipAddress) {
        this.id = id;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
