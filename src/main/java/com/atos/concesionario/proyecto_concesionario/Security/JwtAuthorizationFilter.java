package com.atos.concesionario.proyecto_concesionario.Security;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService; // servicio para cargar usuarios

    public JwtAuthorizationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Verificar si el header Authorization trae un Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Sin token, seguir sin autenticar (la ruta podría ser pública)
        }

        String token = authHeader.replace("Bearer ", "");
        // Validar el token JWT
        String username = jwtUtils.validarToken(token)? jwtUtils.getUsernameFromToken(token):"No Existe Username";
        if (username != null) {
            // Token válido: obtener datos de usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // (Opcional: podríamos evitar esta carga de la BD si también codificamos roles en el token.
            // Aquí usamos userDetailsService para asegurarnos que el usuario existe y está activo).

            // Construir la autenticación para el contexto de Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        // Si el token es inválido o username es null, no se setea autenticación (la petición será rechazada si era requerida)

        filterChain.doFilter(request, response);
    }
}