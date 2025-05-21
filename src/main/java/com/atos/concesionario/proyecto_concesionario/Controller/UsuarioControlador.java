package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {
    

    private final UsuarioServicio usuarioServicio;


    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Endpoints CRUD
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        return ResponseEntity.ok(usuarioServicio.obtenerTodosUsuarios());
    }
    

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return usuarioServicio.obtenerUsuarioPorId(usuarioId);
    }

    @PostMapping("/correo")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestBody Map<String, String> body) throws ResourceNotFoundException {
        String correo = body.get("correo");
        return usuarioServicio.obtenerUsuarioPorCorreo(correo);
    }
    

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        System.out.println("Contrseña recibida: " + usuario.getContrasena());
        return usuarioServicio.crearUsuario(usuario);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long usuarioId, @RequestBody Usuario usuarioDetalles) throws ResourceNotFoundException {
        return usuarioServicio.actualizarUsuario(usuarioId, usuarioDetalles);
    }

    @DeleteMapping("/{usuarioId}")
    public Map<String, Boolean> eliminarUsuario(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return usuarioServicio.eliminarUsuario(usuarioId);
    }

    // Otros endpoints

}
