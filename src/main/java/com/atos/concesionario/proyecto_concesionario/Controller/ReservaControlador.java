package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import com.atos.concesionario.proyecto_concesionario.Repository.ReservaRepositorio;
import jakarta.transaction.Transactional;
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
    private final ReservaRepositorio reservaRepositorio;

    public ReservaControlador(ReservaServicio reservaServicio, ReservaRepositorio reservaRepositorio) {
        this.reservaServicio = reservaServicio;
        this.reservaRepositorio = reservaRepositorio;
    }

    @GetMapping
    public List<Reserva> obtenerTodasReservas() {
        return reservaServicio.obtenerTodasReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return reservaServicio.obtenerReservaPorId(id);
    }

    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaServicio.crearReserva(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaDetalles) throws ResourceNotFoundException {
        return reservaServicio.actualizarReserva(id, reservaDetalles);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarReserva(@PathVariable Long id) throws ResourceNotFoundException {
        return reservaServicio.eliminarReserva(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Reserva> obtenerReservasPorUsuario(@PathVariable Long idUsuario) {
        return reservaServicio.obtenerReservasPorUsuario(idUsuario);
    @Transactional
    @DeleteMapping("/matricula/{matricula}")
    public ResponseEntity<Map<String, Object>> eliminarReservasPorMatricula(@PathVariable String matricula) {
        return reservaServicio.eliminarReservasPorMatricula( matricula);
    }
}