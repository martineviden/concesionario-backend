package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
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

    public Usuario crearUsuario(Usuario usuario) {
    	// Cifrar el DNI con AES
        String dniCifrado = AESUtil.encrypt(usuario.getDni());
    	// Hashear dni y contraseña antes de guardar
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
            System.out.println(" Se ha recibido una contraseña no vacía");

            if (!passwordEncoder.matches(usuarioDetalles.getContrasena(), usuario.getContrasena())) {
                System.out.println(" Contraseña nueva detectada. Hasheando y actualizando...");
                String nuevaContrasenaHasheada = passwordEncoder.encode(usuarioDetalles.getContrasena());
                usuario.setContrasena(nuevaContrasenaHasheada);
            } else {
                System.out.println(" La contraseña recibida es igual a la ya hasheada. No se actualiza.");
            }
        } else {
            System.out.println(" No se recibió una nueva contraseña (nula o en blanco). Se mantiene la actual.");
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

}
