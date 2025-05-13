package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import com.atos.concesionario.proyecto_concesionario.Service.ReservaServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ReservaControlador {

    private final ReservaServicio reservaServicio;

    @Autowired
    public ReservaControlador(ReservaServicio reservaServicio) {
        this.reservaServicio = reservaServicio;
    }

    // Endpoints CRUD

    @GetMapping("/reservas")
    public List<Reserva> obtenerTodasReservas() {
        return reservaServicio.obtenerTodasReservas();
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long reservaId) throws ResourceNotFoundException {
        return reservaServicio.obtenerReservaPorId(reservaId);
    }

    @PostMapping("/crear/reserva")
    public Reserva crearReserva(@Valid @RequestBody Reserva reserva) {
        return reservaServicio.crearReserva(reserva);
    }

    @PutMapping("/reserva/{reservaId}/actualizar")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long reservaId, @Valid @RequestBody Reserva reservaDetalles) throws ResourceNotFoundException {
        return reservaServicio.actualizarReserva(reservaId, reservaDetalles);
    }

    @DeleteMapping("/reserva/{reservaId}/eliminar")
    public Map<String, Boolean> eliminarReserva(@PathVariable Long reservaId) throws ResourceNotFoundException {
        return reservaServicio.eliminarReserva(reservaId);
    }
}