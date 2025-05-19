package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.*;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario.Rol;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Usa H2 si no tienes otra configurada
public class ReservaRepositorioTest {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    @Autowired
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    private Usuario usuario;
    private Vehiculo vehiculo;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        // Crear y guardar usuario
        usuario = new Usuario();

        usuario.setDni("12345678A");
        usuario.setNombre("Rubén");
        usuario.setApellidos("Gil");
        usuario.setCorreo("ruben@example.com");
        usuario.setContrasena("654321");
        usuario.setTelefono("656747219");
        usuario.setRol(Rol.CLIENTE);

        usuarioRepositorio.save(usuario);

        // Crear y guardar tipo de vehículo
        TipoVehiculo tipoVehiculo = new TipoVehiculo();

        tipoVehiculo.setMarca("Toyota");
        tipoVehiculo.setModelo("Corolla");
        tipoVehiculo.setPrecio(20000);
        tipoVehiculo.setTipo(Tipo.COCHE);

        tipoVehiculoRepositorio.save(tipoVehiculo);

        // Crear y guardar vehículo
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
        vehiculo.setUbicacion(Vehiculo.Provincia.MADRID); // NECESARIO

        vehiculoRepositorio.save(vehiculo);

        // Crear y guardar reserva
        reserva = new Reserva();

        reserva.setVehiculo(vehiculo);
        reserva.setUsuario(usuario);
        reserva.setFechaReserva(LocalDate.of(2025, 5, 16));
        reserva.setDiasReserva(2);
        reserva.setPrecio(20.0);

        reservaRepositorio.save(reserva);
    }

    @Test
    void obtenerReservasPorIdUsuario_deberiaRetornarLista() {
        List<Reserva> reservas = reservaRepositorio.findByUsuarioId(usuario.getId());
        assertEquals(1, reservas.size());
        assertEquals(usuario.getId(), reservas.get(0).getUsuario().getId());
    }

    @Test
    void obtenerReservasPorMatricula_deberiaRetornarLista() {
        List<Reserva> reservas = reservaRepositorio.findByVehiculoMatricula("123ABC");
        assertEquals(1, reservas.size());
        assertEquals("123ABC", reservas.get(0).getVehiculo().getMatricula());
    }

    @Test
    void obtenerReservasEntreFechas_deberiaRetornarLista() {
        LocalDate inicio = LocalDate.of(2025, 5, 1);
        LocalDate fin = LocalDate.of(2025, 5, 30);
        List<Reserva> reservas = reservaRepositorio.findByFechaReservaBetween(inicio, fin);
        assertEquals(1, reservas.size());
    }

    @Test
    void obtenerReservasDespuesDeFecha_deberiaRetornarLista() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        List<Reserva> reservas = reservaRepositorio.findByFechaReservaAfter(fecha);
        assertEquals(1, reservas.size());
    }
}
