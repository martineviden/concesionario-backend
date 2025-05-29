package com.atos.concesionario.proyecto_concesionario.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

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
		AuthenticationManager authManager = authenticationManager(http);
		JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(authManager, jwtUtils);
		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtUtils, userDetailsService); // 💡 Instanciado aquí

		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
						.requestMatchers(HttpMethod.POST, "/resenas/**").permitAll()
						.requestMatchers(HttpMethod.PUT, "/resenas/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/resenas/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/reservas/**").permitAll()
						.requestMatchers(HttpMethod.DELETE, "/reservas/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/vehiculos/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/usuarios/**").hasAuthority("ADMIN")
						.anyRequest().authenticated()
				)
				.authenticationProvider(authenticationProvider())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.addFilter(authFilter)
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
