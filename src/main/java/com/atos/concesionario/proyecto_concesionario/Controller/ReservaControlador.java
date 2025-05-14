package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaServicio reservaServicio;


    public ReservaControlador(ReservaServicio reservaServicio) {
        this.reservaServicio = reservaServicio;
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasReservas() {
        return ResponseEntity.ok(reservaServicio.obtenerTodasReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reservaServicio.obtenerReservaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaServicio.crearReserva(reserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
            @PathVariable Long id,
            @RequestBody Reserva reservaDetalles) throws ResourceNotFoundException {
        return ResponseEntity.ok(reservaServicio.actualizarReserva(id, reservaDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) throws ResourceNotFoundException {
        reservaServicio.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}