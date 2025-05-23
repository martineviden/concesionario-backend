package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Security.CustomUserDetailsService;
import com.atos.concesionario.proyecto_concesionario.Service.UsuarioServicio;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;


@RestController
@RequestMapping("/auth")
public class AuthControlador {

	private final AuthenticationManager authenticationManager;

	private final CustomUserDetailsService userDetailsService;

	private final JwtUtils jwtUtils;

	private final UsuarioServicio usuarioServicio;

	private final PasswordEncoder passwordEncoder;

	public AuthControlador(AuthenticationManager authenticationManager,
						   CustomUserDetailsService userDetailsService,
						   JwtUtils jwtUtils,
						   UsuarioServicio usuarioServicio,
						   PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.usuarioServicio = usuarioServicio;
		this.passwordEncoder = passwordEncoder; // ✅ esto enlaza la dependencia
	}

	@PostMapping("/login")
	public ResponseEntity<?> autenticarUsuario(@RequestBody LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena())
			);
		}catch (BadCredentialsException e) {
		System.out.println("❌ Error: BadCredentialsException");
		e.printStackTrace();
		return ResponseEntity.status(401).body("Credenciales inválidas");
	}


	// Cargar el usuario completo
		Usuario usuario = usuarioServicio.obtenerPorCorreo(loginRequest.getCorreo());
		String jwt = jwtUtils.generarToken(usuario.getCorreo(), usuario.getRol().name());

		return ResponseEntity.ok(new JwtResponse(jwt, usuario.getRol().name()));
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
		private String rol;
	}
	@PostMapping("/login-test")
	public ResponseEntity<?> loginTest(@RequestBody LoginRequest loginRequest) {
		try {
			System.out.println("🧪 Probar BCrypt manual:");
			System.out.println("Resultado manual: " + passwordEncoder.matches(
					loginRequest.getContrasena(),
					"$2a$10$S5sTkVK0UUPenFv8UFwPxOBEuZ6go0FIsveFzQnwrJPj7Fu30AZ5G"
			));

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getCorreo(),
							loginRequest.getContrasena()
					)
			);

			return ResponseEntity.ok("✅ Login correcto");

		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body("❌ Login fallido: " + e.getMessage());
		}
	}



}
