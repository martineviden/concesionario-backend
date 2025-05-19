package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Repository.TipoVehiculoRepositorio;

@Service

public class TipoVehiculoServicio {
	private final TipoVehiculoRepositorio tipoVehiculoRepositorio;

	public TipoVehiculoServicio(TipoVehiculoRepositorio tipoVehiculoRepositorio) {
		this.tipoVehiculoRepositorio = tipoVehiculoRepositorio;
	}

	public List<TipoVehiculo> obtenerTodosTiposVehiculos() {
		return tipoVehiculoRepositorio.findAll();
	}

	public ResponseEntity<TipoVehiculo> obtenerTipoVehiculoPorId(Long id) throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehiculo con id " + id + " no encontrado"));
		return ResponseEntity.ok().body(tipoVehiculo);
	}

	public TipoVehiculo crearTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return tipoVehiculoRepositorio.save(tipoVehiculo);
	}

	public ResponseEntity<TipoVehiculo> actualizarTipoVehiculo(Long id, TipoVehiculo detallesTipoVehiculo)
			throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehiculo con id " + id + " no encontrado"));

		tipoVehiculo.setMarca(detallesTipoVehiculo.getMarca());
		tipoVehiculo.setModelo(detallesTipoVehiculo.getModelo());
		tipoVehiculo.setPrecio(detallesTipoVehiculo.getPrecio());
		tipoVehiculo.setTipo(detallesTipoVehiculo.getTipo());
		tipoVehiculo.setImagen(detallesTipoVehiculo.getImagen());

		final TipoVehiculo tipoVehiculoActualizado = tipoVehiculoRepositorio.save(tipoVehiculo);
		return ResponseEntity.ok(tipoVehiculoActualizado);
	}

	public Map<String, Boolean> eliminarTipoVehiculo(Long id) throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehiculo con id " + id + " no encontrado"));

		tipoVehiculoRepositorio.delete(tipoVehiculo);

		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("Tipo de vehiculo eliminado", Boolean.TRUE);
		return respuesta;
	}
}
