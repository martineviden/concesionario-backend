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

    private Usuario usuario;
    private TipoVehiculo tipo;
    private Vehiculo vehiculo;
    private Resena resena;

    @BeforeEach
    void setUp() {
        // Crear y guardar usuario
        usuario = Usuario.builder()
                .dni("87654321B")
                .nombre("Carlos")
                .apellidos("Pérez")
                .correo("carlos@example.com")
                .contrasena("123456")
                .telefono("123456789")
                .rol(Rol.CLIENTE)
                .build();
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
        vehiculo.setMarca("Ford"); // importante: campo duplicado respecto a tipo
        vehiculoRepositorio.save(vehiculo);

        // Crear y guardar reseña
        resena = Resena.builder()
                .comentario("Excelente vehículo")
                .puntuacion(5)
                .fecha(LocalDate.now())
                .usuario(usuario)
                .vehiculo(vehiculo)
                .build();
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
        Resena nuevaResena = Resena.builder()
                .comentario("Buen coche")
                .puntuacion(4)
                .fecha(LocalDate.now())
                .usuario(usuario)
                .vehiculo(vehiculo)
                .build();
        Resena guardada = resenaRepositorio.save(nuevaResena);
        assertNotNull(guardada.getId());
        assertEquals("Buen coche", guardada.getComentario());
    }
}

