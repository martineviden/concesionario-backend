package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import com.atos.concesionario.proyecto_concesionario.Repository.VehiculoRepositorio;

@Service
public class VehiculoServicio {

    private final VehiculoRepositorio vehiculoRepositorio;

    public VehiculoServicio(VehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }

    // Métodos CRUD

    public List<Vehiculo> obtenerTodosVehiculos() {
        return vehiculoRepositorio.findAll();
    }

    public ResponseEntity<Vehiculo> obtenerVehiculoPorMatricula(String matricula) throws ResourceNotFoundException {
        Vehiculo vehiculo = vehiculoRepositorio.findById(matricula)
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo con matrícula " + matricula + " no encontrado"));
        return ResponseEntity.ok().body(vehiculo);
    }

    public List<Vehiculo> obtenerVehiculosPorUbicacion(String ubicacion) throws ResourceNotFoundException {
		return vehiculoRepositorio.findByUbicacion(ubicacion);
	}

    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepositorio.save(vehiculo);
    }

    public ResponseEntity<Vehiculo> actualizarVehiculo(String matricula, Vehiculo detallesVehiculo) throws ResourceNotFoundException {
        Vehiculo vehiculo = vehiculoRepositorio.findById(matricula)
            .orElseThrow(() -> new ResourceNotFoundException("Vehículo con matrícula " + matricula + " no encontrado"));

        vehiculo.setTipoVehiculo(detallesVehiculo.getTipoVehiculo());
        vehiculo.setColor(detallesVehiculo.getColor());
        vehiculo.setKilometraje(detallesVehiculo.getKilometraje());
        vehiculo.setDisponibilidad(detallesVehiculo.getDisponibilidad());
        vehiculo.setCombustible(detallesVehiculo.getCombustible());
        vehiculo.setEtiqueta(detallesVehiculo.getEtiqueta());
        vehiculo.setAutonomia(detallesVehiculo.getAutonomia());
        vehiculo.setPuertas(detallesVehiculo.getPuertas());
        vehiculo.setAireAcondicionado(detallesVehiculo.getAireAcondicionado());
        vehiculo.setPlazas(detallesVehiculo.getPlazas());
        vehiculo.setTransmision(detallesVehiculo.getTransmision());

        final Vehiculo vehiculoActualizado = vehiculoRepositorio.save(vehiculo);
        return ResponseEntity.ok(vehiculoActualizado);
    }

    public Map<String, Boolean> eliminarVehiculo(String matricula) throws ResourceNotFoundException {
        Vehiculo vehiculo = vehiculoRepositorio.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con matrícula: " + matricula));

        vehiculoRepositorio.delete(vehiculo);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Vehiculo eliminado", Boolean.TRUE);
        return respuesta;
    }


    // Otros métodos

    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return vehiculoRepositorio.findByDisponibilidad(true);
    }

    public List<Vehiculo> obtenerVehiculosPorTipo(TipoVehiculo.Tipo tipo) {
        return vehiculoRepositorio.findByTipoVehiculo(tipo);
    }


    
    public List<Vehiculo> buscarPorTipoYUbicacion(TipoVehiculo.Tipo tipo, String ubicacion) {
        return vehiculoRepositorio.findByTipoVehiculo_TipoAndUbicacion(tipo, ubicacion);
    }
    
    // Métodos adicionales podrían incluir:
    // - Buscar vehículos por rango de precio
    // - Actualizar disponibilidad
}