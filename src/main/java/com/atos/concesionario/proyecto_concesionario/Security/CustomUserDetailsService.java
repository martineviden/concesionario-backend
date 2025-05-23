package com.atos.concesionario.proyecto_concesionario.Security;

import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		System.out.println("🔍 Buscando usuario con correo: " + correo);

		Usuario usuario = usuarioRepositorio.findByCorreo(correo)
				.orElseThrow(() -> {
					System.out.println("❌ Usuario no encontrado: " + correo);
					return new UsernameNotFoundException("Usuario no encontrado");
				});

		System.out.println("✅ Usuario encontrado: " + usuario.getCorreo() + " | Rol: " + usuario.getRol());

		return new org.springframework.security.core.userdetails.User(
				usuario.getCorreo(),
				usuario.getContrasena(),
				List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
		);
	}


}
