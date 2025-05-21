package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Usa H2 si no tienes otra configurada
public class ResenaRepositorioTest {
	@Autowired
    private ResenaRepositorio resenaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    private Usuario usuario;
    private Vehiculo vehiculo;
    private Resena resena;

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
        usuarioRepositorio.save(usuario);

        // Crear y guardar vehículo
        vehiculo = new Vehiculo();
        vehiculo.setMatricula("456DEF");
        vehiculo.setColor("Negro");
        vehiculo.setDisponibilidad(true);
        vehiculoRepositorio.save(vehiculo);

        // Crear y guardar reseña
        resena = new Resena();
        resena.setUsuario(usuario);
        resena.setVehiculo(vehiculo);
        resena.setPuntuacion(5);
        resena.setComentario("Excelente vehículo");
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
}