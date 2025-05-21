package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.GlobalTestConfig;
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.SecurityDisabledTestConfig;
import com.atos.concesionario.proyecto_concesionario.Service.ResenaServicio;
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

@WebMvcTest(ResenaControlador.class)
@Import({GlobalTestConfig.class, SecurityDisabledTestConfig.class})
public class ResenaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResenaServicio resenaServicio;

    private Resena resena;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        resena = new Resena();
        resena.setId(1L);
        resena.setComentario("Excelente vehículo");
        resena.setPuntuacion(5);
        // Si tienes setUsuario o setVehiculo, también puedes configurarlos aquí si son necesarios para la lógica
    }

    @Test
    void obtenerTodasResenas_deberiaRetornarLista() throws Exception {
        when(resenaServicio.obtenerTodasResenas()).thenReturn(List.of(resena));

        mockMvc.perform(get("/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].texto").value("Excelente vehículo"));
    }

    @Test
    void obtenerResenaPorId_deberiaRetornarResena() throws Exception {
        when(resenaServicio.obtenerResenaPorId(1L)).thenReturn(ResponseEntity.ok(resena));

        mockMvc.perform(get("/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntuacion").value(5));
    }

    @Test
    void crearResena_deberiaRetornarResenaCreada() throws Exception {
        when(resenaServicio.crearResena(any(Resena.class))).thenReturn(resena);

        mockMvc.perform(post("/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.texto").value("Excelente vehículo"));
    }

    @Test
    void actualizarResena_deberiaRetornarResenaActualizada() throws Exception {
        when(resenaServicio.actualizarResena(eq(1L), any(Resena.class))).thenReturn(ResponseEntity.ok(resena));

        mockMvc.perform(put("/resenas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntuacion").value(5));
    }

    @Test
    void eliminarResena_deberiaRetornarConfirmacion() throws Exception {
        Map<String, Boolean> resultado = new HashMap<>();
        resultado.put("Resena eliminada", true);

        when(resenaServicio.eliminarResena(1L)).thenReturn(resultado);

        mockMvc.perform(delete("/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['Resena eliminada']").value(true));
    }
}
