package com.atos.concesionario.proyecto_concesionario.Controller;

import com.atos.concesionario.proyecto_concesionario.GlobalTestConfig;
import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario.Rol;
import com.atos.concesionario.proyecto_concesionario.SecurityDisabledTestConfig;
import com.atos.concesionario.proyecto_concesionario.Service.ReservaServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaControlador.class)
@Import({GlobalTestConfig.class, SecurityDisabledTestConfig.class})
class ReservaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaServicio reservaServicio;

    private Reserva reserva;
    private Vehiculo vehiculo;
    private TipoVehiculo tipoVehiculo;
    private Usuario usuario;
    private ObjectMapper objectMapper;

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
        vehiculo.setCombustible(Combustible.GASOLINA);
        vehiculo.setEtiqueta(EtiquetaAmbiental.C);
        vehiculo.setAutonomia(400);
        vehiculo.setPuertas(4);
        vehiculo.setAireAcondicionado(true);
        vehiculo.setPlazas(5);
        vehiculo.setTransmision(Transmision.AUTOMATICO);

        reserva = new Reserva();

        reserva.setVehiculo(vehiculo);
        reserva.setUsuario(usuario);
        reserva.setFechaReserva(LocalDate.of(2025, 5, 16));
        reserva.setDiasReserva(2);
        reserva.setPrecio(20.0);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void obtenerTodasReservas_deberiaRetornarLista() throws Exception {
        when(reservaServicio.obtenerTodasReservas()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/reservas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].precio").value(20.0));
    }

    @Test
    void obtenerReservaPorId_deberiaRetornarReserva() throws Exception {
        when(reservaServicio.obtenerReservaPorId(1L)).thenReturn(ResponseEntity.ok(reserva));

        mockMvc.perform(get("/reservas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.precio").value(20.0));
    }

    @Test
    void crearReserva_deberiaRetornarReservaCreada() throws Exception {
        when(reservaServicio.crearReserva(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/reservas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(20.0));
    }

    @Test
    void actualizarReserva_deberiaRetornarReservaActualizada() throws Exception {
        when(reservaServicio.actualizarReserva(eq(1L), any(Reserva.class))).thenReturn(ResponseEntity.ok(reserva));

        mockMvc.perform(put("/reservas/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(20.0));
    }

    @Test
    void eliminarReserva_deberiaRetornarConfirmacion() throws Exception {
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Reserva eliminada", true);

        when(reservaServicio.eliminarReserva(1L)).thenReturn(respuesta);

        mockMvc.perform(delete("/reservas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['Reserva eliminada']").value(true));
    }

}
