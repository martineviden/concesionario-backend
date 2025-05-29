package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    public UsuarioControlador(UsuarioServicio usuarioServicio, PasswordEncoder passwordEncoder) {
        this.usuarioServicio = usuarioServicio;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario, Authentication auth) {
        // Si alguien intenta crear un ADMIN...
        if (usuario.getRol() == Usuario.Rol.ADMIN) {
            // Solo permitirlo si está autenticado y es ADMIN
            if (auth == null || auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return ResponseEntity.status(403).build();
            }
        }

        Usuario creado = usuarioServicio.crearUsuario(usuario);
        creado.setContrasena(null); // seguridad extra
        return ResponseEntity.ok(creado);
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
