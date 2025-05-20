package com.atos.concesionario.proyecto_concesionario.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario.Rol;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositorioTest {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();

        usuario.setDni("12345678A");
        usuario.setNombre("Rubén");
        usuario.setApellidos("Gil");
        usuario.setCorreo("ruben@example.com");
        usuario.setContrasena("654321");
        usuario.setTelefono("656747219");
        usuario.setRol(Rol.CLIENTE);

        usuarioRepositorio.save(usuario);
    }

    @Test
    void obtenerUsuariosPorRol_deberiaRetornarLista() {
        List<Usuario> usuarios = usuarioRepositorio.findByRol(usuario.getRol());
        assertEquals(1, usuarios.size());
        assertEquals(Rol.CLIENTE, usuarios.get(0).getRol());
    }

}
