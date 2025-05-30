package com.atos.concesionario.proyecto_concesionario.Config;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.atos.concesionario.proyecto_concesionario.Security.CustomUserDetailsService;
import com.atos.concesionario.proyecto_concesionario.Security.JwtAuthorizationFilter;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

	private final CustomUserDetailsService userDetailsService;

	private final JwtUtils jwtUtils;

	public SeguridadConfig(CustomUserDetailsService userDetailsService, JwtUtils jwtUtils) {
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authBuilder.authenticationProvider(authenticationProvider());
		return authBuilder.build();
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtUtils, userDetailsService); // 💡 Instanciado aquí

		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**").permitAll()


						.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios/**").hasAuthority("ADMIN")

						.requestMatchers(HttpMethod.POST, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.GET, "/vehiculos/**").permitAll()

						.requestMatchers(HttpMethod.POST, "/resenas/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/resenas/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/resenas/**").permitAll()

						.requestMatchers(HttpMethod.POST, "/reservas/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/reservas/**").permitAll()

						.requestMatchers(HttpMethod.GET,"/tipos-vehiculo").permitAll()
						.requestMatchers(HttpMethod.GET,"/tipos-vehiculo/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/tipos-vehiculo/").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/tipos-vehiculo/").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/tipos-vehiculo/").permitAll()

						.requestMatchers(HttpMethod.GET,"/especificaciones/**").permitAll()


						// Todo lo demás requiere autenticación
						.anyRequest().authenticated()
				)

				.authenticationProvider(authenticationProvider())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		config.setExposedHeaders(List.of("Authorization"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
