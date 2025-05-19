package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.GlobalTestConfig;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.SecurityDisabledTestConfig;
import com.atos.concesionario.proyecto_concesionario.Service.TipoVehiculoServicio;
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

@WebMvcTest(TipoVehiculoControlador.class)
@Import({GlobalTestConfig.class, SecurityDisabledTestConfig.class})
public class TipoVehiculoControladorTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TipoVehiculoServicio tipoVehiculoServicio;

    private TipoVehiculo tipoVehiculo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tipoVehiculo = new TipoVehiculo();

        tipoVehiculo.setMarca("Toyota");
        tipoVehiculo.setModelo("Corolla");
        tipoVehiculo.setPrecio(20000);
        tipoVehiculo.setTipo(Tipo.COCHE);

        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerTodosTiposVehiculos_deberiaRetornarList() throws Exception {
        when(tipoVehiculoServicio.obtenerTodosTiposVehiculos()).thenReturn(List.of(tipoVehiculo));

        mockMvc.perform(get("/tipos-vehiculo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].precio").value(20000));
    }

    @Test
    void obtenerTipoVehiculoPorId_deberiaRetornarTipoVehiculo() throws Exception {
        when(tipoVehiculoServicio.obtenerTipoVehiculoPorId(1L)).thenReturn(ResponseEntity.ok(tipoVehiculo));

        mockMvc.perform(get("/tipos-vehiculo/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.precio").value(20000));
    }

    @Test
    void crearTipoVehiculo_deberiaRetornarTipoVehiculoCreado() throws Exception {
        when(tipoVehiculoServicio.crearTipoVehiculo(any(TipoVehiculo.class))).thenReturn(tipoVehiculo);

        mockMvc.perform(post("/tipos-vehiculo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tipoVehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(20000));
    }

    @Test
    void actualizarTipoVehiculo_deberiaRetornarTipoVehiculoActualizado() throws Exception {
        when(tipoVehiculoServicio.actualizarTipoVehiculo(eq(1L), any(TipoVehiculo.class))).thenReturn(ResponseEntity.ok(tipoVehiculo));

        mockMvc.perform(put("/tipos-vehiculo/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tipoVehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(20000));
    }

    @Test
    void eliminarTipoVehiculo_deberiaRetornarConfirmacion() throws Exception {
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Tipo de vehiculo eliminado", true);

        when(tipoVehiculoServicio.eliminarTipoVehiculo(1L)).thenReturn(respuesta);

        mockMvc.perform(delete("/tipos-vehiculo/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['Tipo de vehiculo eliminado']").value(true));
    }

}
