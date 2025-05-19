package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.GlobalTestConfig;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.SecurityDisabledTestConfig;
import com.atos.concesionario.proyecto_concesionario.Service.UsuarioServicio;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioControlador.class)
@Import({GlobalTestConfig.class, SecurityDisabledTestConfig.class})
class UsuarioControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioServicio usuarioServicio;

    private Usuario usuario;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        
        usuario.setId(1L);
        usuario.setCorreo("test@mail.com");
        usuario.setNombre("Prueba");
        usuario.setRol(Usuario.Rol.CLIENTE);

        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerTodosUsuarios_deberiaRetornarLista() throws Exception {
        when(usuarioServicio.obtenerTodosUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].correo").value("test@mail.com"));
    }

    @Test
    void obtenerUsuarioPorId_deberiaRetornarUsuario() throws Exception {
        when(usuarioServicio.obtenerUsuarioPorId(1L)).thenReturn(ResponseEntity.ok(usuario));

        mockMvc.perform(get("/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correo").value("test@mail.com"));
    }

    @Test
    void crearUsuario_deberiaRetornarUsuarioCreado() throws Exception {
        when(usuarioServicio.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@mail.com"));
    }

    @Test
    void actualizarUsuario_deberiaRetornarUsuarioActualizado() throws Exception {
        when(usuarioServicio.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(ResponseEntity.ok(usuario));

        mockMvc.perform(put("/usuarios/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("test@mail.com"));
    }

    @Test
    void eliminarUsuario_deberiaRetornarConfirmacion() throws Exception {
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Usuario eliminado", true);

        when(usuarioServicio.eliminarUsuario(1L)).thenReturn(respuesta);

        mockMvc.perform(delete("/usuarios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['Usuario eliminado']").value(true));
    }
}
