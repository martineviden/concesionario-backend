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
	        // 1. Verificar primero si el usuario existe
	        Usuario usuario = usuarioServicio.obtenerPorCorreo(loginRequest.getCorreo());
	        if (usuario == null) {
	            return ResponseEntity.status(401).body("Usuario no encontrado");
	        }

	        // 2. Autenticación con Spring Security
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getCorreo(),
	                loginRequest.getContrasena()
	            )
	        );

	        // 3. Generar JWT si la autenticación fue exitosa
	        String jwt = jwtUtils.generarToken(usuario.getCorreo(), usuario.getRol().name());
	        
	        return ResponseEntity.ok(new JwtResponse(jwt, usuario.getRol().name()));

	    } catch (BadCredentialsException e) {
	        System.err.println("Error en credenciales para: " + loginRequest.getCorreo());
	        return ResponseEntity.status(401).body("Credenciales inválidas");
	    } catch (AuthenticationException e) {
	        System.err.println("Error de autenticación: " + e.getMessage());
	        return ResponseEntity.status(401).body("Error en autenticación");
	    }
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


	@PostMapping("/verificar-contrasena")
	public String verificarContrasena(@RequestBody Map<String, String> payload) {
		String correo = payload.get("correo");
		String contrasenaPlano = payload.get("contrasena");

		Usuario usuario = usuarioRepositorio.findByCorreo(correo)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		String hashEnBD = usuario.getContrasena();

		boolean coincide = passwordEncoder.matches(contrasenaPlano, hashEnBD);

		return coincide ? "✅ Coincide" : "❌ No coincide";
	}



}
