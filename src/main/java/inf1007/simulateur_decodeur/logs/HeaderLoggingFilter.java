//package inf1007.simulateur_decodeur.logs;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class HeaderLoggingFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        System.out.println("🔍 Requête entrante : " + request.getMethod() + " " + request.getRequestURI());
//        System.out.println("🔐 Header Authorization = " + authHeader);
//
//        filterChain.doFilter(request, response);
//    }
//}
