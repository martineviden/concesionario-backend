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
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Service.ResenaServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ResenaControlador {

    private final ResenaServicio resenaServicio;

    @Autowired
    public ResenaControlador(ResenaServicio resenaServicio) {
        this.resenaServicio = resenaServicio;
    }

    // Endpoints CRUD

    @GetMapping("/resenas")
    public List<Resena> obtenerTodasResenas() {
        return resenaServicio.obtenerTodasResenas();
    }

    @GetMapping("/resena/{resenaId}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long resenaId) throws ResourceNotFoundException {
        return resenaServicio.obtenerResenaPorId(resenaId);
    }

    @PostMapping("/crear/resena")
    public Resena crearResena(@Valid @RequestBody Resena resena) {
        return resenaServicio.crearResena(resena);
    }

    @PutMapping("/resena/{resenaId}/actualizar")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long resenaId, @Valid @RequestBody Resena resenaDetalles) throws ResourceNotFoundException {
        return resenaServicio.actualizarResena(resenaId, resenaDetalles);
    }

    @DeleteMapping("/resena/{resenaId}/eliminar")
    public Map<String, Boolean> eliminarResena(@PathVariable Long resenaId) throws ResourceNotFoundException {
        return resenaServicio.eliminarResena(resenaId);
    }

    // Otros endpoints

}