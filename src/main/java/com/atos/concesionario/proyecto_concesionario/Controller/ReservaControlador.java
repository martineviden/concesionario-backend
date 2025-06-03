package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
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
    private final UsuarioRepositorio usuarioRepositorio;

    public ReservaControlador(ReservaServicio reservaServicio, UsuarioRepositorio usuarioRepositorio) {
        this.reservaServicio = reservaServicio;
        this.usuarioRepositorio = usuarioRepositorio;
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

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado"));
        }

        List<Reserva> reservas = reservaServicio.obtenerReservasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

}