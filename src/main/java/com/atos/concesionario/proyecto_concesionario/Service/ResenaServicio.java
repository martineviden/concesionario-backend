package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Repository.ResenaRepositorio;

@Service
public class ResenaServicio {

    private final ResenaRepositorio resenaRepositorio;

    @Autowired
    public ResenaServicio(ResenaRepositorio resenaRepositorio) {
        this.resenaRepositorio = resenaRepositorio;
    }

    // Métodos CRUD

    public List<Resena> obtenerTodasResenas() {
        return resenaRepositorio.findAll();
    }

    public ResponseEntity<Resena> obtenerResenaPorId(Long resenaId) throws ResourceNotFoundException {
        Resena resena = resenaRepositorio.findById(resenaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + resenaId + " no encontrada"));
        
        return ResponseEntity.ok().body(resena);
    }

    public Resena crearResena(Resena resena) {
        return resenaRepositorio.save(resena);
    }

    public ResponseEntity<Resena> actualizarResena(Long resenaId, Resena resenaDetalles) throws ResourceNotFoundException {
        Resena resena = resenaRepositorio.findById(resenaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + resenaId + " not found"));
        
        resena.setTexto(resenaDetalles.getTexto());
        resena.setPuntuacion(resenaDetalles.getPuntuacion());
        resena.setFecha(resenaDetalles.getFecha());
        resena.setUsuario(resenaDetalles.getUsuario());
        resena.setVehiculo(resenaDetalles.getVehiculo());
        resena.setReserva(resenaDetalles.getReserva());

        final Resena resenaActualizada = resenaRepositorio.save(resena);
        return ResponseEntity.ok(resenaActualizada);
    }

    public Map<String, Boolean> eliminarResena(Long resenaId) throws ResourceNotFoundException {
        Resena resena = resenaRepositorio.findById(resenaId)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + resenaId + " no encontrada"));
        
        resenaRepositorio.delete(resena);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Reseña eliminada", Boolean.TRUE);
        return respuesta;
    }

    // Otros métodos

}