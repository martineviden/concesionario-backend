package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Service.UsuarioServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Endpoints CRUD

    @GetMapping("/usuarios")
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioServicio.obtenerTodosUsuarios();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return usuarioServicio.obtenerUsuarioPorId(usuarioId);
    }

    @PostMapping("/crear/usuario")
    public Usuario crearUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioServicio.crearUsuario(usuario);
    }

    @PutMapping("/usuario/{usuarioId}/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long usuarioId, @Valid @RequestBody Usuario usuarioDetalles) throws ResourceNotFoundException {
        return usuarioServicio.actualizarUsuario(usuarioId, usuarioDetalles);
    }

    @DeleteMapping("/usuario/{usuarioId}/eliminar")
    public Map<String, Boolean> eliminarUsuario(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return usuarioServicio.eliminarUsuario(usuarioId);
    }

    // Otros endpoints

}
