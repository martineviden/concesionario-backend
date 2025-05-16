package com.atos.concesionario.proyecto_concesionario.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario.Rol;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import com.atos.concesionario.proyecto_concesionario.Repository.ReservaRepositorio;
import com.atos.concesionario.proyecto_concesionario.Repository.TipoVehiculoRepositorio;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
import com.atos.concesionario.proyecto_concesionario.Repository.VehiculoRepositorio;

public class ReservaServicioTest {

    @Mock
    private ReservaRepositorio reservaRepositorio;

    @Mock
    private UsuarioRepositorio UsuarioRepositorio;

    @Mock
    private VehiculoRepositorio vehiculoRepositorio;

    @Mock
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    @InjectMocks
    private ReservaServicio reservaServicio;

    private Reserva reserva;
    private Usuario usuario;
    private Vehiculo vehiculo;
    private TipoVehiculo tipoVehiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    }

    @Test
    void obtenerTodasReservas_deberiaRetornarLista() {
        when(reservaRepositorio.findAll()).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaServicio.obtenerTodasReservas();

        assertEquals(1, resultado.size());
        verify(reservaRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerReservaPorId_deberiaRetornarReserva() throws ResourceNotFoundException {
        when(reservaRepositorio.findById(1L)).thenReturn(Optional.of(reserva));

        ResponseEntity<Reserva> response = reservaServicio.obtenerReservaPorId(1L);

        assertEquals(20.0, response.getBody().getPrecio());
        verify(reservaRepositorio).findById(1L);
    }

    @Test
    void obtenerReservaPorId_deberiaLanzarExcepcion() {
        when(reservaRepositorio.findById(9999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reservaServicio.obtenerReservaPorId(9999L);
        });
    }

    @Test
    void crearReserva_deberiaGuardarYRetornarReserva() {
        when(reservaRepositorio.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaServicio.crearReserva(reserva);

        assertEquals("2025-05-16", resultado.getFechaReserva());
        verify(reservaRepositorio).save(reserva);
    }

    @Test
    void actualizarReserva_deberiaActualizarYRetornarReserva() throws ResourceNotFoundException {
        Reserva reservaActualizada = new Reserva();
        
        reservaActualizada.setVehiculo(vehiculo);
        reservaActualizada.setUsuario(usuario);
        reservaActualizada.setFechaReserva(LocalDate.of(2025, 6, 22));
        reservaActualizada.setDiasReserva(5);
        reservaActualizada.setPrecio(30.0);

        when(reservaRepositorio.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepositorio.save(any())).thenReturn(reservaActualizada);

        ResponseEntity<Reserva> response = reservaServicio.actualizarReserva(1L, reservaActualizada);

        assertEquals(30.0, response.getBody().getPrecio());
        verify(reservaRepositorio).save(any());
    }

    @Test
    void eliminarReserva_deberiaEliminarYRetornarConfirmacion() throws ResourceNotFoundException {
        when(reservaRepositorio.findById(1L)).thenReturn(Optional.of(reserva));

        Map<String, Boolean> resultado = reservaServicio.eliminarReserva(1L);

        assertTrue(resultado.get("Reserva eliminada"));
        verify(reservaRepositorio).delete(reserva);
    }

}
