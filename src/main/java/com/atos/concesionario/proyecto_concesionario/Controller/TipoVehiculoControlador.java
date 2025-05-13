package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Service.TipoVehiculoServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TipoVehiculoControlador {
	
	private final TipoVehiculoServicio tipoVehiculoServicio;

	@Autowired
	public TipoVehiculoControlador(TipoVehiculoServicio tipoVehiculoServicio) {
		this.tipoVehiculoServicio = tipoVehiculoServicio;
	}

	// Endpoints CRUD

	@GetMapping("/tipos-vehiculos")
	public List<TipoVehiculo> obtenerTodosTiposVehiculos() {
		return tipoVehiculoServicio.obtenerTodosTiposVehiculos();
	}

	@GetMapping("/tipo-vehiculo/{tipoVehiculoId}")
	public ResponseEntity<TipoVehiculo> obtenerTipoVehiculoPorId(@PathVariable Long tipoVehiculoId) throws ResourceNotFoundException {
		return tipoVehiculoServicio.obtenerTipoVehiculoPorId(tipoVehiculoId);
	}

	@PostMapping("/crear/tipo-vehiculo")
	public TipoVehiculo crearTipoVehiculo(@Valid @RequestBody TipoVehiculo tipoVehiculo) {
		return tipoVehiculoServicio.crearTipoVehiculo(tipoVehiculo);
	}

	@PutMapping("/tipo-vehiculo/{tipoVehiculoId}/actualizar")
	public ResponseEntity<TipoVehiculo> actualizarTipoVehiculo(@PathVariable Long tipoVehiculoId, @Valid @RequestBody TipoVehiculo tipoVehiculoDetalles) throws ResourceNotFoundException {
		return tipoVehiculoServicio.actualizarTipoVehiculo(tipoVehiculoId, tipoVehiculoDetalles);
	}

	@DeleteMapping("/tipo-vehiculo/{tipoVehiculoId}/eliminar")
	public Map<String, Boolean> eliminarTipoVehiculo(@PathVariable Long tipoVehiculoId) throws ResourceNotFoundException {
		return tipoVehiculoServicio.eliminarTipoVehiculo(tipoVehiculoId);
	}

	// Otros endpoints
}
