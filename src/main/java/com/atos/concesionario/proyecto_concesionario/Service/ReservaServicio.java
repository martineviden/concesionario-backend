package com.atos.concesionario.proyecto_concesionario.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import com.atos.concesionario.proyecto_concesionario.Repository.ReservaRepositorio;

@Service
public class ReservaServicio {

    private final ReservaRepositorio reservaRepositorio;

    @Autowired
    public ReservaServicio(ReservaRepositorio reservaRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
    }

    // Métodos CRUD

    public List<Reserva> obtenerTodasReservas() {
        return reservaRepositorio.findAll();
    }

    public ResponseEntity<Reserva> obtenerReservaPorId(Long reservaId) throws ResourceNotFoundException {
        Reserva reserva = reservaRepositorio.findById(reservaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + reservaId + " no encontrada"));
        
        return ResponseEntity.ok().body(reserva);
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    public ResponseEntity<Reserva> actualizarReserva(Long reservaId, Reserva reservaDetalles) throws ResourceNotFoundException {
        Reserva reserva = reservaRepositorio.findById(reservaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + reservaId + " not encontrada"));

        reserva.setVehiculo(reservaDetalles.getVehiculo());
        reserva.setUsuario(reservaDetalles.getUsuario());
        reserva.setFechaReserva(reservaDetalles.getFechaReserva());
        reserva.setDiasReserva(reservaDetalles.getDiasReserva());
        reserva.setPrecio(reservaDetalles.getPrecio());
        
        final Reserva reservaActualizada = reservaRepositorio.save(reserva);
        return ResponseEntity.ok(reservaActualizada);
    }

    public Map<String, Boolean> eliminarReserva(Long reservaId) throws ResourceNotFoundException {
        Reserva reserva = reservaRepositorio.findById(reservaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + reservaId + " no encontrada"));

        reservaRepositorio.delete(reserva);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Reserva eliminada", Boolean.TRUE);
        return respuesta;
    }

    // Otros métodos

}