package MicroServicio.Stock.infrastructure.configuration.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // Servicio que ya debería estar encargado de manejar JWT (generación, validación, etc.)

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;

        // Validar el encabezado del token JWT
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT
        jwt = authorizationHeader.substring(7);

        // Validar el token JWT y extraer los claims
        if (jwtService.isTokenValid(jwt)) {
            String email = jwtService.extractUsername(jwt); // En este caso, 'sub' sería el username
            var claims = jwtService.extractAllClaims(jwt);

            // Crear la autenticación manualmente usando los claims del token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    email,
                    null,  // No necesitamos password en este caso
                    jwtService.extractAuthorities(claims) // Extraer las autoridades/roles del token
            );

            // Configurar el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}