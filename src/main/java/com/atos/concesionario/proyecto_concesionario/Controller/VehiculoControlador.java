package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Service.VehiculoServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class VehiculoControlador {
	
	private final VehiculoServicio vehiculoServicio;

	@Autowired
	public VehiculoControlador(VehiculoServicio vehiculoServicio) {
		this.vehiculoServicio = vehiculoServicio;
	}

	// Endpoints CRUD

	@GetMapping("/vehiculos")
	public List<Vehiculo> obtenerTodosVehiculos() {
		return vehiculoServicio.obtenerTodosVehiculos();
	}

	@GetMapping("/matricula/{matricula}")
	public ResponseEntity<Vehiculo> obtenerVehiculoPorMatricula(@PathVariable String matricula)
			throws ResourceNotFoundException {
		return vehiculoServicio.obtenerVehiculoPorMatricula(matricula);
	}

	@PostMapping("/crear/vehiculo")
	public Vehiculo crearVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
		return vehiculoServicio.crearVehiculo(vehiculo);
	}

	@PutMapping("/matricula/{matricula}/actualizar")
	public ResponseEntity<Vehiculo> actualizarVehiculoPorMatricula(@PathVariable String matricula, @Valid @RequestBody Vehiculo vehiculoDetalles) throws ResourceNotFoundException {
		return vehiculoServicio.actualizarVehiculoPorMatricula(matricula, vehiculoDetalles);
	}

	@DeleteMapping("/matricula/{matricula}/eliminar")
	public Map<String, Boolean> eliminarVehiculoPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
		return vehiculoServicio.eliminarVehiculoPorMatricula(matricula);
	}

	// Otros endpoints

}
