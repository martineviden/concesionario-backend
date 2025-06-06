package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Service.TipoVehiculoServicio;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tipos-vehiculo")
public class TipoVehiculoControlador {
	private final TipoVehiculoServicio tipoVehiculoServicio;

	public TipoVehiculoControlador(TipoVehiculoServicio tipoVehiculoServicio) {
		this.tipoVehiculoServicio = tipoVehiculoServicio;
	}

	@GetMapping
	public List<TipoVehiculo> obtenerTodosTiposVehiculos() {
		return tipoVehiculoServicio.obtenerTodosTiposVehiculos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoVehiculo> obtenerTipoVehiculoPorId(@PathVariable Long id) throws ResourceNotFoundException {
		return tipoVehiculoServicio.obtenerTipoVehiculoPorId(id);
	}

	@PostMapping
	public TipoVehiculo crearTipoVehiculo(@RequestHeader("Content-Type") String contentType,
										  @RequestBody TipoVehiculo tipoVehiculo) {

		System.out.println("📥 Content-Type recibido: " + contentType);
		System.out.println("📦 Datos recibidos:");
		System.out.println("  Marca: " + tipoVehiculo.getMarca());
		System.out.println("  Modelo: " + tipoVehiculo.getModelo());
		System.out.println("  Precio: " + tipoVehiculo.getPrecio());
		System.out.println("  Tipo: " + tipoVehiculo.getTipo());
		System.out.println("  Imagen: " + tipoVehiculo.getImagen());

		return tipoVehiculoServicio.crearTipoVehiculo(tipoVehiculo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoVehiculo> actualizarTipoVehiculo(@PathVariable Long id,
			@RequestBody TipoVehiculo tipoVehiculoDetalles) throws ResourceNotFoundException {
		return tipoVehiculoServicio.actualizarTipoVehiculo(id, tipoVehiculoDetalles);
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> eliminarTipoVehiculo(@PathVariable Long id) throws ResourceNotFoundException {
		return tipoVehiculoServicio.eliminarTipoVehiculo(id);
	}
}
