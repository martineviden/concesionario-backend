package com.atos.concesionario.proyecto_concesionario.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;
import com.atos.concesionario.proyecto_concesionario.Repository.TipoVehiculoRepositorio;

public class TipoVehiculoServicioTest {
    
    @Mock
    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

    @InjectMocks
    private TipoVehiculoServicio tipoVehiculoServicio;

    private TipoVehiculo tipoVehiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tipoVehiculo = new TipoVehiculo();

        tipoVehiculo.setMarca("Toyota");
        tipoVehiculo.setModelo("Corolla");
        tipoVehiculo.setPrecio(20000);
        tipoVehiculo.setTipo(Tipo.COCHE);
    }

    @Test
    void obtenerTodosTiposVehiculos_deberiaRetornarLista() {
        when(tipoVehiculoRepositorio.findAll()).thenReturn(List.of(tipoVehiculo));

        List<TipoVehiculo> resultado = tipoVehiculoServicio.obtenerTodosTiposVehiculos();

        assertEquals(1, resultado.size());
        verify(tipoVehiculoRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerTipoVehiculoPorId_deberiaRetornarTipoVehiculo() throws Exception {
        when(tipoVehiculoRepositorio.findById(1L)).thenReturn(Optional.of(tipoVehiculo));

        ResponseEntity<TipoVehiculo> response = tipoVehiculoServicio.obtenerTipoVehiculoPorId(1L);

        assertNotNull(response.getBody());
        assertEquals(20000, response.getBody().getPrecio());
        verify(tipoVehiculoRepositorio).findById(1L);
    }

    @Test
    void obtenerTipoVehiculoPorId_deberiaLanzarExcepcion() {
        when(tipoVehiculoRepositorio.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tipoVehiculoServicio.obtenerTipoVehiculoPorId(1L));
    }

    @Test
    void crearTipoVehiculo_deberiaGuardarYRetornarTipoVehiculo() {
        when(tipoVehiculoRepositorio.save(tipoVehiculo)).thenReturn(tipoVehiculo);

        TipoVehiculo resultado = tipoVehiculoServicio.crearTipoVehiculo(tipoVehiculo);

        assertEquals(20000, resultado.getPrecio());
        verify(tipoVehiculoRepositorio).save(tipoVehiculo);
    }

    @Test
    void actualizarTipoVehiculo_deberiaActualizarYRetornarTipoVehiculo() throws Exception {
        TipoVehiculo actualizado = new TipoVehiculo();

        actualizado.setMarca("BMW");
        actualizado.setModelo("Serie 3");
        actualizado.setPrecio(60000);
        actualizado.setTipo(Tipo.COCHE);

        when(tipoVehiculoRepositorio.findById(1L)).thenReturn(Optional.of(tipoVehiculo));
        when(tipoVehiculoRepositorio.save(any())).thenReturn(actualizado);

        ResponseEntity<TipoVehiculo> response = tipoVehiculoServicio.actualizarTipoVehiculo(1L, actualizado);

        assertNotNull(response.getBody());
        assertEquals(60000, response.getBody().getPrecio());
        verify(tipoVehiculoRepositorio).save(any());
    }

    @Test
    void eliminarTipoVehiculo_deberiaEliminarYRetornarConfirmacion() throws Exception {
        when(tipoVehiculoRepositorio.findById(1L)).thenReturn(Optional.of(tipoVehiculo));

        Map<String, Boolean> resultado = tipoVehiculoServicio.eliminarTipoVehiculo(1L);

        assertTrue(resultado.get("Tipo de vehiculo eliminado"));
        verify(tipoVehiculoRepositorio).delete(tipoVehiculo);
    }

}
