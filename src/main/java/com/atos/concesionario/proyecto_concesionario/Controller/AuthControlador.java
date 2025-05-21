package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.atos.concesionario.proyecto_concesionario.Security.CustomUserDetailsService;
import com.atos.concesionario.proyecto_concesionario.Security.JwtTokenFilter;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final AuthenticationManager authenticationManager;

    private final  CustomUserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;

    public AuthControlador(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getCorreo());
        String jwt = jwtUtils.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @Data
    public static class LoginRequest {
        private String correo;
        private String contrasena;
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }

    // Bean AuthenticationManager: maneja la autenticación (usado por JwtAuthenticationFilter)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
        // Spring obtendrá automáticamente userDetailsService y passwordEncoder para construir el manager:contentReference[oaicite:3]{index=3}:contentReference[oaicite:4]{index=4}.
    }

    // Cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Registro de nuestros filtros personalizados
        JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtils);
        JwtTokenFilter jwtAuthorizationFilter = new JwtTokenFilter(jwtUtils, userDetailsService);

        http.csrf(csrf -> csrf.disable())  // Desactivar CSRF (no se usa con API stateless JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesión de servidor
                .authorizeHttpRequests(auth -> {
                    // Rutas públicas (permitir sin autenticación):
                    auth.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();         // registro de usuarios
                    auth.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();   // login
                    auth.requestMatchers(HttpMethod.POST, "/resenas/**").permitAll();       // crear reseña
                    auth.requestMatchers(HttpMethod.PUT, "/resenas/**").permitAll();        // editar reseña
                    auth.requestMatchers(HttpMethod.DELETE, "/resenas/**").permitAll();     // eliminar reseña
                    auth.requestMatchers(HttpMethod.POST, "/reservas/**").permitAll();      // crear reserva
                    auth.requestMatchers(HttpMethod.DELETE, "/reservas/**").permitAll();    // cancelar/eliminar reserva

                    // Rutas protegidas solo para ADMIN:
                    auth.requestMatchers(HttpMethod.POST, "/vehiculos/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT,  "/vehiculos/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/vehiculos/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN");

                    // Cualquier otra petición requiere estar autenticado (login básico):
                    auth.anyRequest().authenticated();
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // habilitar CORS con configuración proporcionada
                .addFilter(authFilter)  // filtro de autenticación (login)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);  // filtro JWT antes del filtro por defecto

        return http.build();
    }
}
