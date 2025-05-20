package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Provincia;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class VehiculoRepositorioTest {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    @Autowired
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    private Vehiculo vehiculo;
    private TipoVehiculo tipoVehiculo;

    @BeforeEach
    void setUp() {
        tipoVehiculo = new TipoVehiculo();
        tipoVehiculo.setMarca("Toyota");
        tipoVehiculo.setModelo("Corolla");
        tipoVehiculo.setPrecio(20000);
        tipoVehiculo.setTipo(TipoVehiculo.Tipo.COCHE);

        tipoVehiculo = tipoVehiculoRepositorio.save(tipoVehiculo); // guardar explícitamente

        vehiculo = new Vehiculo();
        vehiculo.setMatricula("123ABC");
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setColor("Blanco");
        vehiculo.setKilometraje(10000);
        vehiculo.setDisponibilidad(true);
        vehiculo.setCombustible(Combustible.GASOLINA);
        vehiculo.setEtiqueta(EtiquetaAmbiental.C);
        vehiculo.setAutonomia(400);
        vehiculo.setPuertas(4);
        vehiculo.setAireAcondicionado(true);
        vehiculo.setPlazas(5);
        vehiculo.setTransmision(Transmision.AUTOMATICO);
        vehiculo.setUbicacion(Provincia.MADRID);

        vehiculoRepositorio.save(vehiculo);
    }

    @Test
    void testFindByDisponibilidad() {
        List<Vehiculo> disponibles = vehiculoRepositorio.findByDisponibilidad(true);
        assertThat(disponibles).isNotEmpty();
    }

    @Test
    void testFindByUbicacion() {
        List<Vehiculo> ubicados = vehiculoRepositorio.findByUbicacion(Provincia.MADRID);
        assertThat(ubicados).isNotEmpty();
    }

    @Test
    void testFindByTipoVehiculo() {
        List<Vehiculo> coches = vehiculoRepositorio.findByTipoVehiculo(TipoVehiculo.Tipo.COCHE);
        assertThat(coches).isNotEmpty();
    }

    @Test
    void testFindByTipoVehiculo_TipoAndUbicacion() {
        List<Vehiculo> resultados = vehiculoRepositorio.findByTipoVehiculo_TipoAndUbicacion(TipoVehiculo.Tipo.COCHE, Provincia.MADRID);
        assertThat(resultados).isNotEmpty();
    }
}
