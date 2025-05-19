package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.GlobalTestConfig;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Provincia;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.SecurityDisabledTestConfig;
import com.atos.concesionario.proyecto_concesionario.Service.VehiculoServicio;
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

@WebMvcTest(VehiculoControlador.class)
@Import({GlobalTestConfig.class, SecurityDisabledTestConfig.class})
class VehiculoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehiculoServicio vehiculoServicio;

    private Vehiculo vehiculo;
    private TipoVehiculo tipoVehiculo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setMarca("Toyota");
        tipoVehiculo.setModelo("Corolla");
        tipoVehiculo.setPrecio(20000);
        tipoVehiculo.setTipo(Tipo.COCHE);

        vehiculo = new Vehiculo();
        vehiculo.setMatricula("123ABC");
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setColor("Blanco");
        vehiculo.setKilometraje(100000);
        vehiculo.setDisponibilidad(true);
        vehiculo.setUbicacion(Provincia.MADRID);
        vehiculo.setCombustible(Combustible.GASOLINA);
        vehiculo.setEtiqueta(EtiquetaAmbiental.C);
        vehiculo.setAutonomia(400);
        vehiculo.setPuertas(4);
        vehiculo.setAireAcondicionado(true);
        vehiculo.setPlazas(5);
        vehiculo.setTransmision(Transmision.AUTOMATICO);

        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerTodosVehiculos_deberiaRetornarLista() throws Exception {
        when(vehiculoServicio.obtenerTodosVehiculos()).thenReturn(List.of(vehiculo));

        mockMvc.perform(get("/vehiculos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].color").value("Blanco"));
    }

    @Test
    void crearVehiculo_deberiaRetornarVehiculoCreado() throws Exception {
        when(vehiculoServicio.crearVehiculo(any(Vehiculo.class))).thenReturn(vehiculo);

        mockMvc.perform(post("/vehiculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Blanco"));
    }

    @Test
    void obtenerVehiculoPorMatricula_deberiaRetornarVehiculo() throws Exception {
        when(vehiculoServicio.obtenerVehiculoPorMatricula("123ABC")).thenReturn(ResponseEntity.ok(vehiculo));

        mockMvc.perform(get("/vehiculos/123ABC"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.color").value("Blanco"));
    }

    @Test
    void actualizarVehiculo_deberiaRetornarVehiculoActualizado() throws Exception {
        when(vehiculoServicio.actualizarVehiculo(eq("123ABC"), any(Vehiculo.class))).thenReturn(ResponseEntity.ok(vehiculo));

        mockMvc.perform(put("/vehiculos/123ABC")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Blanco"));
    }

    @Test
    void eliminarVehiculo_deberiaRetornarConfirmacion() throws Exception {
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Vehiculo eliminado", true);

        when(vehiculoServicio.eliminarVehiculo("123ABC")).thenReturn(respuesta);

        mockMvc.perform(delete("/vehiculos/123ABC"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['Vehiculo eliminado']").value(true));
    }

}