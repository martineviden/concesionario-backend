package com.atos.concesionario.proyecto_concesionario.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServicioTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("test@mail.com");
        usuario.setNombre("Nombre");
        usuario.setRol(Usuario.Rol.CLIENTE);
    }

    @Test
    void obtenerTodosUsuarios_deberiaRetornarLista() {
        when(usuarioRepositorio.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioServicio.obtenerTodosUsuarios();

        assertEquals(1, resultado.size());
        verify(usuarioRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerUsuarioPorId_deberiaRetornarUsuario() throws Exception {
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioServicio.obtenerUsuarioPorId(1L);

        assertEquals("test@mail.com", response.getBody().getCorreo());
        verify(usuarioRepositorio).findById(1L);
    }

    @Test
    void obtenerUsuarioPorId_deberiaLanzarExcepcion() {
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioServicio.obtenerUsuarioPorId(1L);
        });
    }

    @Test
    void crearUsuario_deberiaGuardarYRetornarUsuario() {
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServicio.crearUsuario(usuario);

        assertEquals("test@mail.com", resultado.getCorreo());
        verify(usuarioRepositorio).save(usuario);
    }

    @Test
    void actualizarUsuario_deberiaActualizarYRetornarUsuario() throws Exception {
        Usuario actualizado = new Usuario();
        actualizado.setDni("12345678A");
        actualizado.setNombre("Nuevo");
        actualizado.setApellidos("Apellido");
        actualizado.setCorreo("nuevo@mail.com");
        actualizado.setContrasena("1234");
        actualizado.setTelefono("600123123");
        actualizado.setRol(Usuario.Rol.ADMIN);

        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepositorio.save(any())).thenReturn(actualizado);

        ResponseEntity<Usuario> response = usuarioServicio.actualizarUsuario(1L, actualizado);

        assertEquals("nuevo@mail.com", response.getBody().getCorreo());
        verify(usuarioRepositorio).save(any());
    }

    @Test
    void eliminarUsuario_deberiaEliminarYRetornarConfirmacion() throws Exception {
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));

        Map<String, Boolean> resultado = usuarioServicio.eliminarUsuario(1L);

        assertTrue(resultado.get("Usuario eliminado"));
        verify(usuarioRepositorio).delete(usuario);
    }
}
