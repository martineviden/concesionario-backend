package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo.Tipo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Usa H2 si no tienes otra configurada
public class TipoVehiculoRepositorioTest {
	 @Autowired
	    private TipoVehiculoRepositorio tipoVehiculoRepositorio;

	    private TipoVehiculo tipoVehiculo;

	    @BeforeEach
	    void setUp() {
	        // Crear y guardar tipo de vehículo
	        tipoVehiculo = new TipoVehiculo();
	        tipoVehiculo.setTipo(Tipo.COCHE);
	        tipoVehiculo.setMarca("Ford");
	        tipoVehiculo.setModelo("Focus");
	        tipoVehiculo.setPrecio(22000);
	        tipoVehiculoRepositorio.save(tipoVehiculo);
	    }

	    @Test
	    void guardarYEncontrarTipoVehiculo_deberiaRetornarTipoVehiculo() {
	        TipoVehiculo encontrado = tipoVehiculoRepositorio.findById(tipoVehiculo.getId()).orElse(null);
	        assertNotNull(encontrado);
	        assertEquals(tipoVehiculo.getMarca(), encontrado.getMarca());
	        assertEquals(tipoVehiculo.getModelo(), encontrado.getModelo());
	    }

	    @Test
	    void eliminarTipoVehiculo_deberiaNoEncontrarTipoVehiculo() {
	        tipoVehiculoRepositorio.delete(tipoVehiculo);
	        assertFalse(tipoVehiculoRepositorio.findById(tipoVehiculo.getId()).isPresent());
	    }
}
