package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.*;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario.Rol;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Provincia;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;

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
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ResenaRepositorioTest {

    @Autowired
    private ResenaRepositorio resenaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;
    
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    private Usuario usuario;
    private TipoVehiculo tipo;
    private Vehiculo vehiculo;
    private Resena resena;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
    	// Crear y guardar usuario
    	usuario = new Usuario();
    	usuario.setDni("87654321B");
    	usuario.setNombre("Carlos");
    	usuario.setApellidos("Pérez");
    	usuario.setCorreo("carlos@example.com");
    	usuario.setContrasena("123456");
    	usuario.setTelefono("123456789");
    	usuario.setRol(Rol.CLIENTE);
    	usuarioRepositorio.save(usuario);

    	// Crear y guardar tipo de vehículo
    	tipo = new TipoVehiculo();
    	tipo.setTipo(Tipo.COCHE);
    	tipo.setMarca("Ford");
    	tipo.setModelo("Focus");
    	tipo.setPrecio(22000);
    	tipoVehiculoRepositorio.save(tipo);

    	// Crear y guardar vehículo
    	vehiculo = new Vehiculo();
    	vehiculo.setMatricula("456DEF");
    	vehiculo.setTipoVehiculo(tipo);
    	vehiculo.setColor("Negro");
    	vehiculo.setDisponibilidad(true);
    	vehiculo.setUbicacion(Provincia.MADRID);
    	vehiculo.setPuertas(4);
    	vehiculo.setPlazas(5);
    	vehiculo.setAireAcondicionado(true);
    	vehiculo.setKilometraje(15000);
    	vehiculo.setAutonomia(600);
    	vehiculo.setCombustible(Combustible.GASOLINA);
    	vehiculo.setEtiqueta(EtiquetaAmbiental.C);
    	vehiculo.setTransmision(Transmision.MANUAL);
    	vehiculoRepositorio.save(vehiculo);

    	// Crear y guardar una reserva
    	reserva = new Reserva();
    	reserva.setFechaReserva(LocalDate.now());
    	reserva.setDiasReserva(5);
    	reserva.setPrecio(5000.2);
    	reserva.setUsuario(usuario);
    	reserva.setVehiculo(vehiculo);
    	reservaRepositorio.save(reserva);

    	// Crear y guardar reseña
    	resena = new Resena();
    	resena.setComentario("Excelente vehículo");
    	resena.setPuntuacion(5);
    	resena.setFecha(LocalDate.now());
    	resena.setUsuario(usuario);
    	resena.setVehiculo(vehiculo);
    	resena.setReserva(reserva);
    	resenaRepositorio.save(resena);

    }

    @Test
    void obtenerResenasPorIdUsuario_deberiaRetornarLista() {
        List<Resena> resenas = resenaRepositorio.findByUsuarioId(usuario.getId());
        assertEquals(1, resenas.size());
        assertEquals(usuario.getId(), resenas.get(0).getUsuario().getId());
    }

    @Test
    void obtenerResenasPorMatricula_deberiaRetornarLista() {
        List<Resena> resenas = resenaRepositorio.findByVehiculoMatricula("456DEF");
        assertEquals(1, resenas.size());
        assertEquals("456DEF", resenas.get(0).getVehiculo().getMatricula());
    }

    @Test
    void obtenerResenasPorPuntuacion_deberiaRetornarLista() {
        List<Resena> resenas = resenaRepositorio.findByPuntuacionGreaterThanEqual(4);
        assertEquals(1, resenas.size());
        assertEquals(5, resenas.get(0).getPuntuacion());
    }

    @Test
    void guardarResena_conDatosCompletos_deberiaPersistir() {
        Resena nuevaResena = new Resena();
        nuevaResena.setComentario("Buen coche");
        nuevaResena.setPuntuacion(4);
        nuevaResena.setFecha(LocalDate.now());
        nuevaResena.setUsuario(usuario);
        nuevaResena.setVehiculo(vehiculo);
       
        Resena guardada = resenaRepositorio.save(nuevaResena);
        assertNotNull(guardada.getId());
        assertEquals("Buen coche", guardada.getComentario());
    }
}

