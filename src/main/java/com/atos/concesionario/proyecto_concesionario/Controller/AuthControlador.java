package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.Jwt.JwtUtils;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
import com.atos.concesionario.proyecto_concesionario.Security.CustomUserDetailsService;
import com.atos.concesionario.proyecto_concesionario.Service.UsuarioServicio;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthControlador {

	private final AuthenticationManager authenticationManager;

	private final CustomUserDetailsService userDetailsService;

	private final JwtUtils jwtUtils;

	private final UsuarioServicio usuarioServicio;

	private final UsuarioRepositorio usuarioRepositorio;

	private final PasswordEncoder passwordEncoder;

	public AuthControlador(AuthenticationManager authenticationManager,
                           CustomUserDetailsService userDetailsService,
                           JwtUtils jwtUtils,
                           UsuarioServicio usuarioServicio, UsuarioRepositorio usuarioRepositorio,
                           PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtils = jwtUtils;
		this.usuarioServicio = usuarioServicio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder; // ✅ esto enlaza la dependencia
	}

	@PostMapping("/login")
	public ResponseEntity<?> autenticarUsuario(@RequestBody LoginRequest loginRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getCorreo(),
							loginRequest.getContrasena()
					)
			);
		} catch (BadCredentialsException e) {
			System.out.println("❌ Error: BadCredentialsException");
			return ResponseEntity.status(401).body(Map.of("autenticado", false, "mensaje", "Credenciales inválidas"));
		}

		Usuario usuario = usuarioServicio.obtenerPorCorreo(loginRequest.getCorreo());

		// Limpiar la contraseña antes de enviar
		usuario.setContrasena(null);

		// Generar el token
		String jwt = jwtUtils.generarToken(usuario.getCorreo(), usuario.getRol().name());

		LoginResponse response = new LoginResponse(true, usuario, jwt, "Skin del tio ese 🐋😎🎁");
		return ResponseEntity.ok(response);
	}


	@Data
	public static class LoginRequest {
		private String correo;
		private String contrasena;
	}




	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginResponse {
		private boolean autenticado;
		private Usuario usuario;
		private String token;
		private String regalo;
	}

}
