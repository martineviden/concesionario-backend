package com.atos.concesionario.proyecto_concesionario.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Combustible;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.EtiquetaAmbiental;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo.Transmision;
import com.atos.concesionario.proyecto_concesionario.Repository.TipoVehiculoRepositorio;
import com.atos.concesionario.proyecto_concesionario.Repository.VehiculoRepositorio;

public class VehiculoServicioTest {

    @Mock
    private VehiculoRepositorio vehiculoRepositorio;

    @Mock
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    @InjectMocks
    private VehiculoServicio vehiculoServicio;

    private Vehiculo vehiculo;
    private TipoVehiculo tipoVehiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
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
    }

    @Test
    void obtenerTodosVehiculos_deberiaRetornarLista() {
        when(vehiculoRepositorio.findAll()).thenReturn(List.of(vehiculo));

        List<Vehiculo> resultado = vehiculoServicio.obtenerTodosVehiculos();

        assertEquals(1, resultado.size());
        verify(vehiculoRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerVehiculoPorMatricula_deberiaRetornarVehiculo() throws ResourceNotFoundException {
        when(vehiculoRepositorio.findById("123ABC")).thenReturn(Optional.of(vehiculo));

        ResponseEntity<Vehiculo> response = vehiculoServicio.obtenerVehiculoPorMatricula("123ABC");

        assertEquals(100000, response.getBody().getKilometraje());
        verify(vehiculoRepositorio).findById("123ABC");
    }

}
