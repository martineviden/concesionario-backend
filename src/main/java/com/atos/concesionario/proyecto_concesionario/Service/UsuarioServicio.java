package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;

@Service
public class UsuarioServicio {
    
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // Métodos CRUD

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public ResponseEntity<Usuario> obtenerUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));

        return ResponseEntity.ok().body(usuario);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public ResponseEntity<Usuario> actualizarUsuario(Long usuarioId, Usuario usuarioDetalles) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));

        usuario.setDni(usuarioDetalles.getDni());
        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setApellidos(usuarioDetalles.getApellidos());
        usuario.setCorreo(usuarioDetalles.getCorreo());
        usuario.setContrasena(usuarioDetalles.getContrasena());
        usuario.setTelefono(usuarioDetalles.getTelefono());
        usuario.setRol(usuarioDetalles.getRol());

        final Usuario usuarioActualizado = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    public Map<String, Boolean> eliminarUsuario(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));
       
        usuarioRepositorio.delete(usuario);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Usuario eliminado", Boolean.TRUE);
        return respuesta;
    }

    // Otros métodos

}
