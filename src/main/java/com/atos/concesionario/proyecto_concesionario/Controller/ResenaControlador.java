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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<Resena>> obtenerTodasResenas(
            @RequestParam(required = false) Integer pagina,
            @RequestParam(required = false) Integer tamaño) {
        
        if (pagina != null && tamaño != null) {
            return ResponseEntity.ok(resenaServicio.obtenerResenasPaginadas(pagina, tamaño).getContent());
        }
        return ResponseEntity.ok(resenaServicio.obtenerTodasResenas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(resenaServicio.obtenerResenaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resenaServicio.crearResena(resena));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(
            @PathVariable Long id, 
           @RequestBody Resena resenaDetalles) throws ResourceNotFoundException {
        return ResponseEntity.ok(resenaServicio.actualizarResena(id, resenaDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) throws ResourceNotFoundException {
        resenaServicio.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }
}