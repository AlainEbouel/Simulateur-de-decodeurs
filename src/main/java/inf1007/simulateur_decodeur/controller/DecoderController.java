//package inf1007.simulateur_decodeur.controller;
//
//import inf1007.simulateur_decodeur.model.Decoder;
//import inf1007.simulateur_decodeur.service.DecoderService;
//import inf1007.simulateur_decodeur.service.UserService;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping
//public class DecoderController {
//
//    private final DecoderService decoderService;
//    private final UserService userService;
//
//    public DecoderController(DecoderService decoderService, UserService userService) {
//        this.decoderService = decoderService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/client/decoders")
//    public List<Decoder> getClientDecoders(Authentication authentication) {
//        return decoderService.getDecodersForCurrentClient(authentication.getName());
//    }
//
//    @PostMapping("/admin/decoders")
//    public Decoder assignDecoder(@RequestBody Decoder decoder) {
//        return decoderService.assignDecoder(decoder);
//    }
//
//    @DeleteMapping("/admin/decoders/{id}")
//    public void deleteDecoder(@PathVariable Long id) {
//        decoderService.removeDecoder(id);
//    }
//}
