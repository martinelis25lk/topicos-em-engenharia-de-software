package br.com.lasanhaspec.carservice.filter;


import br.com.lasanhaspec.carservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    public JwtAuthFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{


        //pega o header authorization do request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;



        // 2. se não tem header ou não começa com "Bearer ", deixa passar

        if (authHeader == null ||  !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. extrai o token removendo o prefixo "Bearer "
        jwt = authHeader.substring(7);



        //4 etxrai o email de dentro do token
        userEmail = jwtService.extractUserName(jwt);


        // 5. se tem email e o usuário ainda não está autenticado
        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // 6. valida o token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7. registra o usuário como autenticado no Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}



