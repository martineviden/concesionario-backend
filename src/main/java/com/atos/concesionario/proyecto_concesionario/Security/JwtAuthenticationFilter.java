package com.atos.concesionario.proyecto_concesionario.Security;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;

		// Especificar la ruta de login personalizada (debe coincidir con el controlador)
		setFilterProcessesUrl("/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getCorreo(),
					creds.getContrasena());

			return authenticationManager.authenticate(authToken);

		} catch (IOException e) {
			throw new RuntimeException("Error al procesar las credenciales de login", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String correo = authResult.getName(); // getUsername()
		String token = jwtUtils.generarToken(correo);

		// Opcional: devolver el token en el body como JSON
		Map<String, String> body = new HashMap<>();
		body.put("token", token);

		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), body);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Error: autenticación fallida");
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginRequest {
		private String correo;
		private String contrasena;
	}
}
