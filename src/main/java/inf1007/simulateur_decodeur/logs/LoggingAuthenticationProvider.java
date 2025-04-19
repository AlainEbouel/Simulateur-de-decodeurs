//package inf1007.simulateur_decodeur.logs;
//
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class LoggingAuthenticationProvider extends DaoAuthenticationProvider {
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
//        String rawPassword = authentication.getCredentials().toString();
//        String encodedPassword = userDetails.getPassword();
//
//        PasswordEncoder encoder = getPasswordEncoder();
//
//        String encodedRaw = encoder.encode(rawPassword);
//
//        System.out.println("🟡 Mot fourni (raw)             : " + rawPassword);
//        System.out.println("🔐 Mot fourni (encodé avec encoder) : " + encodedRaw);
//        System.out.println("🔐 Mot depuis la base (hashé)   : " + encodedPassword);
//
//        if (!encoder.matches(rawPassword, encodedPassword)) {
//            throw new BadCredentialsException("Bad credentials");
//        }
//    }
//}
