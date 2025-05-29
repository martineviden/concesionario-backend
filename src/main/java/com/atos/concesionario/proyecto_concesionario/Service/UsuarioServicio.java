package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.LoginResponse;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
import com.atos.concesionario.proyecto_concesionario.Security.AESUtil;


@Service
public class UsuarioServicio {
    
	private final PasswordEncoder passwordEncoder;
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    // Métodos CRUD

    public List<Usuario> obtenerTodosUsuarios() {
    	return usuarioRepositorio.findAll();
    }

    public ResponseEntity<Usuario> obtenerUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));

        return ResponseEntity.ok().body(usuario);
    }

    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(String correo) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElseThrow(() -> new ResourceNotFoundException("Usuario con correo " + correo + " no encontrado"));

        return ResponseEntity.ok().body(usuario);
    }

    public Usuario crearUsuario(Usuario usuario) {
    	// Hashear dni y contraseña antes de guardar
        String dniHasheado = passwordEncoder.encode(usuario.getDni());
        String contrasenaHasheada = passwordEncoder.encode(usuario.getContrasena());

        usuario.setDni(dniCifrado);
        usuario.setContrasena(contrasenaHasheada);
    	return usuarioRepositorio.save(usuario);
    }
    
    public ResponseEntity<Usuario> obtenerDNIPorId(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));

        // Desencriptar DNI antes de devolverlo
        String dniDesencriptado = AESUtil.decrypt(usuario.getDni());
        usuario.setDni(dniDesencriptado);

        return ResponseEntity.ok().body(usuario);
    }

    public ResponseEntity<Usuario> actualizarUsuario(Long usuarioId, Usuario usuarioDetalles) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));

        // Cifrar el nuevo DNI (solo si no es nulo ni igual al actual en texto claro)
        if (usuarioDetalles.getDni() != null) {
            String nuevoDniCifrado = AESUtil.encrypt(usuarioDetalles.getDni());
            usuario.setDni(nuevoDniCifrado);
        }

        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setApellidos(usuarioDetalles.getApellidos());
        usuario.setCorreo(usuarioDetalles.getCorreo());

        // Hashear nueva contraseña si se actualiza
        if (usuarioDetalles.getContrasena() != null && !usuarioDetalles.getContrasena().isBlank()) {
            String nuevaContrasenaHasheada = passwordEncoder.encode(usuarioDetalles.getContrasena());
            usuario.setContrasena(nuevaContrasenaHasheada);
        }

        usuario.setTelefono(usuarioDetalles.getTelefono());
        usuario.setRol(usuarioDetalles.getRol());

        final Usuario usuarioActualizado = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }


    public Map<String, Boolean> eliminarUsuario(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));
        usuarioRepositorio.delete(usuario);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Usuario eliminado", Boolean.TRUE);
        return respuesta;
    }

    public Usuario obtenerPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // Otros métodos

    public LoginResponse autenticarUsuario(String correo, String contrasenaIngresada) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario con correo " + correo + " no encontrado");
            return new LoginResponse(false, null);
        }

        Usuario usuario = usuarioOpt.get();

        boolean coincide = passwordEncoder.matches(contrasenaIngresada, usuario.getContrasena());

        if (coincide) {
            usuario.setContrasena(null); // Limpia la contraseña antes de devolver el objeto
            return new LoginResponse(true, usuario);
        } else {
            return new LoginResponse(false, null);
        }

    }

}
