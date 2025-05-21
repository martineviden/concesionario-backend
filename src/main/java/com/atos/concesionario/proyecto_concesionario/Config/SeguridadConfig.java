package com.atos.concesionario.proyecto_concesionario.Config;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.atos.concesionario.proyecto_concesionario.Security.CustomUserDetailsService;
import com.atos.concesionario.proyecto_concesionario.Security.JwtAuthenticationFilter;
import com.atos.concesionario.proyecto_concesionario.Security.JwtAuthorizationFilter;
import com.atos.concesionario.proyecto_concesionario.Security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SeguridadConfig {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(new JwtTokenFilter(jwtUtils, userDetailsService),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// Cadena de filtros de seguridad
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Registro de nuestros filtros personalizados
		JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtils);
		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtUtils, userDetailsService);

		http.csrf(csrf -> csrf.disable()) // Desactivar CSRF (no se usa con API stateless JWT)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesión de servidor
				.authorizeHttpRequests(auth -> {
					// Rutas públicas (permitir sin autenticación):
					auth.requestMatchers(HttpMethod.POST, "/usuarios").permitAll(); // registro de usuarios
					auth.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll(); // login
					auth.requestMatchers(HttpMethod.POST, "/resenas/**").permitAll(); // crear reseña
					auth.requestMatchers(HttpMethod.PUT, "/resenas/**").permitAll(); // editar reseña
					auth.requestMatchers(HttpMethod.DELETE, "/resenas/**").permitAll(); // eliminar reseña
					auth.requestMatchers(HttpMethod.POST, "/reservas/**").permitAll(); // crear reserva
					auth.requestMatchers(HttpMethod.DELETE, "/reservas/**").permitAll(); // cancelar/eliminar reserva

					// Rutas protegidas solo para ADMIN:
					auth.requestMatchers(HttpMethod.POST, "/vehiculos/**").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.PUT, "/vehiculos/**").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.DELETE, "/vehiculos/**").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN");

					// Cualquier otra petición requiere estar autenticado (login básico):
					auth.anyRequest().authenticated();
				}).cors(cors -> cors.configurationSource(corsConfigurationSource())) // habilitar CORS con configuración
																						// proporcionada
				.addFilter(authFilter) // filtro de autenticación (login)
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // filtro JWT antes del filtro por defecto

		return http.build();
	}

	// Configuración global de CORS: permitir origen del frontend Angular y
	// headers/metodos necesarios
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200")); // origen del Angular dev server
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		config.setExposedHeaders(List.of("Authorization")); // opcional: expone el header Authorization a JS
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
