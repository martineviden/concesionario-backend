package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Repository.TipoVehiculoRepositorio;

@Service

public class TipoVehiculoServicio {
	
	private final TipoVehiculoRepositorio tipoVehiculoRepositorio;

	@Autowired
	public TipoVehiculoServicio(TipoVehiculoRepositorio tipoVehiculoRepositorio) {
		this.tipoVehiculoRepositorio = tipoVehiculoRepositorio;
	}

	// Métodos CRUD

	public List<TipoVehiculo> obtenerTodosTiposVehiculos() {
		return tipoVehiculoRepositorio.findAll();
	}

	public ResponseEntity<TipoVehiculo> obtenerTipoVehiculoPorId(Long tipoVehiculoId) throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(tipoVehiculoId)
			.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehículo con id " + tipoVehiculoId + " no encontrado"));
		
		return ResponseEntity.ok().body(tipoVehiculo);
	}

	public TipoVehiculo crearTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return tipoVehiculoRepositorio.save(tipoVehiculo);
	}

	public ResponseEntity<TipoVehiculo> actualizarTipoVehiculo(Long tipoVehiculoId, TipoVehiculo detallesTipoVehiculo) throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(tipoVehiculoId)
			.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehículo con id " + tipoVehiculoId + " no encontrado"));

		tipoVehiculo.setMarca(detallesTipoVehiculo.getMarca());
		tipoVehiculo.setModelo(detallesTipoVehiculo.getModelo());
		tipoVehiculo.setPrecio(detallesTipoVehiculo.getPrecio());
		tipoVehiculo.setTipo(detallesTipoVehiculo.getTipo());
		tipoVehiculo.setImagen(detallesTipoVehiculo.getImagen());

		final TipoVehiculo tipoVehiculoActualizado = tipoVehiculoRepositorio.save(tipoVehiculo);
		return ResponseEntity.ok(tipoVehiculoActualizado);
	}

	public Map<String, Boolean> eliminarTipoVehiculo(Long tipoVehiculoId) throws ResourceNotFoundException {
		TipoVehiculo tipoVehiculo = tipoVehiculoRepositorio.findById(tipoVehiculoId)
			.orElseThrow(() -> new ResourceNotFoundException("Tipo de vehículo con id " + tipoVehiculoId + " no encontrado"));

		tipoVehiculoRepositorio.delete(tipoVehiculo);

		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("Tipo de vehículo eliminado", Boolean.TRUE);
		return respuesta;
	}

	// Otros métodos

}
