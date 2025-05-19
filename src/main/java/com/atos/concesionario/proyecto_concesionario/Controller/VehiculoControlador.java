package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Service.VehiculoServicio;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vehiculos")
public class VehiculoControlador {
	private final VehiculoServicio vehiculoServicio;

	public VehiculoControlador(VehiculoServicio vehiculoServicio) {
		this.vehiculoServicio = vehiculoServicio;
	}

	@GetMapping
	public List<Vehiculo> obtenerTodosVehiculos() {
		return vehiculoServicio.obtenerTodosVehiculos();
	}

	@GetMapping("/{matricula}")
	public ResponseEntity<Vehiculo> obtenerVehiculoPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
		return vehiculoServicio.obtenerVehiculoPorMatricula(matricula);
	}

	@GetMapping("/ubicacion/{ubicacion}")
	public List<Vehiculo> obtenerVehiculosPorUbicacion(@PathVariable String ubicacion) throws ResourceNotFoundException {
		return vehiculoServicio.obtenerVehiculosPorUbicacion(ubicacion);
	}

	@PostMapping
	public Vehiculo crearVehiculo(@RequestBody Vehiculo vehiculo) {
		return vehiculoServicio.crearVehiculo(vehiculo);
	}

	@PutMapping("/{matricula}")
	public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable String matricula,
			@RequestBody Vehiculo vehiculoDetalles) throws ResourceNotFoundException {
		return vehiculoServicio.actualizarVehiculo(matricula, vehiculoDetalles);
	}

	@DeleteMapping("/{matricula}")
	public Map<String, Boolean> eliminarVehiculo(@PathVariable String matricula) throws ResourceNotFoundException {
		return vehiculoServicio.eliminarVehiculo(matricula);
	}
	
	@GetMapping("/buscar")
	public List<Vehiculo> buscarPorTipoYUbicacion(
	        @RequestParam TipoVehiculo.Tipo tipo,
	        @RequestParam String ubicacion) {
	    return vehiculoServicio.buscarPorTipoYUbicacion(tipo, ubicacion);
	}
}
