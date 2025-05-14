package com.atos.concesionario.proyecto_concesionario.Service;


import java.util.List;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import com.atos.concesionario.proyecto_concesionario.Repository.ReservaRepositorio;

@Service
public class ReservaServicio {

    private final ReservaRepositorio reservaRepositorio;


    public ReservaServicio(ReservaRepositorio reservaRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
    }

    public List<Reserva> obtenerTodasReservas() {
        return reservaRepositorio.findAll();
    }

    public Reserva obtenerReservaPorId(Long id) throws ResourceNotFoundException {
        return reservaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    public Reserva actualizarReserva(Long id, Reserva reservaDetalles) throws ResourceNotFoundException {
        Reserva reserva = obtenerReservaPorId(id);
        // Actualizar campos
        reserva.setFechaReserva(reservaDetalles.getFechaReserva());
        reserva.setDiasReserva(reservaDetalles.getDiasReserva());
        return reservaRepositorio.save(reserva);
    }

    public void eliminarReserva(Long id) throws ResourceNotFoundException {
        Reserva reserva = obtenerReservaPorId(id);
        reservaRepositorio.delete(reserva);
    }
}