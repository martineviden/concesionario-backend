package com.atos.concesionario.proyecto_concesionario.Controller;

import java.util.List;
import java.util.Map;

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
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Service.ResenaServicio;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/resenas")
public class ResenaControlador {

    private final ResenaServicio resenaServicio;

    public ResenaControlador(ResenaServicio resenaServicio) {
        this.resenaServicio = resenaServicio;
    }

    @GetMapping
    public List<Resena> obtenerTodasResenas() {
        return resenaServicio.obtenerTodasResenas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return resenaServicio.obtenerResenaPorId(id);
    }

    @GetMapping("/matricula/{matricula}")
    public List<Resena> obtenerResenasPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
        return resenaServicio.obtenerResenasPorMatricula(matricula);
    }

    @PostMapping
    public Resena crearResena(@RequestBody Resena resena) {
        return resenaServicio.crearResena(resena);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @RequestBody Resena resenaDetalles) throws ResourceNotFoundException {
        return resenaServicio.actualizarResena(id, resenaDetalles);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> eliminarResena(@PathVariable Long id) throws ResourceNotFoundException {
        return resenaServicio.eliminarResena(id);
    }

    @Transactional
    @DeleteMapping("/matricula/{matricula}")
    public ResponseEntity<Map<String, Object>> eliminarResenasPorMatricula(@PathVariable String matricula){
        return resenaServicio.eliminarResenasPorMatricula( matricula);
    }
}