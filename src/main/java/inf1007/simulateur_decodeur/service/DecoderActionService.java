package inf1007.simulateur_decodeur.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DecoderActionService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://wflageol-uqtr.net/decoder";
    @Value("${simulateur.codePermanent}")
    private String CODE_PERMANENT;


    public String performAction(String ipAddress, String action) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", CODE_PERMANENT);
        requestBody.put("address", ipAddress);
        requestBody.put("action", action);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
        return response.getBody();
    }

    public String getStatus(String ipAddress) {
        return performAction(ipAddress, "info");
    }

    public String reboot(String ipAddress) {
        return performAction(ipAddress, "reset");
    }

    public String reinit(String ipAddress) {
        return performAction(ipAddress, "reinit");
    }

    public String shutdown(String ipAddress) {
        return performAction(ipAddress, "shutdown");
    }
    public String addChannel(String ipAddress, Long channelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Map<String, Object> body = new HashMap<>();
        body.put("id", CODE_PERMANENT);
        body.put("address", ipAddress);
        body.put("action", "add-channel");
        body.put("channelId", channelId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
        return response.getBody();
    }


    public String removeChannel(String ipAddress, Long channelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Map<String, Object> body = new HashMap<>();
        body.put("id", CODE_PERMANENT);
        body.put("address", ipAddress);
        body.put("action", "remove-channel");
        body.put("channelId", channelId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
        return response.getBody();
    }


    public String getChannels(String ipAddress) {
        return restTemplate.getForObject(API_URL  + "/" + ipAddress + "/channels", String.class);
    }
}
