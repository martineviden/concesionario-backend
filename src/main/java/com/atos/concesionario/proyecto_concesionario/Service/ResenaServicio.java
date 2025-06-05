package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Repository.ResenaRepositorio;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ResenaServicio {

    private final ResenaRepositorio resenaRepositorio;

    public ResenaServicio(ResenaRepositorio resenaRepositorio) {
        this.resenaRepositorio = resenaRepositorio;
    }

    // Métodos CRUD

    public List<Resena> obtenerTodasResenas() {
        return resenaRepositorio.findAll();
    }

    public Page<Resena> obtenerResenasPaginadas(int pagina, int tamaño) {
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by("fecha").descending());
        return resenaRepositorio.findAll(pageable);
    }

    public ResponseEntity<Resena> obtenerResenaPorId(Long id) throws ResourceNotFoundException {
        Resena resena = resenaRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + id + " no encontrada"));
        return ResponseEntity.ok().body(resena);
    }

    public List<Resena> obtenerResenasPorMatricula(String matricula) {
        return resenaRepositorio.findByVehiculoMatricula(matricula);
    }

    public Resena crearResena(Resena resena) {
        return resenaRepositorio.save(resena);
    }

    public ResponseEntity<Resena> actualizarResena(Long id, Resena resenaDetalles) throws ResourceNotFoundException {
    	Resena resena = resenaRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + id + " no encontrada"));

        resena.setComentario(resenaDetalles.getComentario());
        resena.setPuntuacion(resenaDetalles.getPuntuacion());
        resena.setFecha(resenaDetalles.getFecha());
        resena.setUsuario(resenaDetalles.getUsuario());
        resena.setVehiculo(resenaDetalles.getVehiculo());
        resena.setReserva(resenaDetalles.getReserva());
        
        final Resena resenaActualizada = resenaRepositorio.save(resena);
        return ResponseEntity.ok(resenaActualizada);
    }

    public Map<String, Boolean> eliminarResena(Long id) throws ResourceNotFoundException {
        Resena resena = resenaRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reseña con id " + id + " no encontrada"));
        
        resenaRepositorio.delete(resena);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Reseña eliminada", Boolean.TRUE);
        return respuesta;
    }

    public ResponseEntity<Map<String, Object>> eliminarResenasPorMatricula( String matricula){
        int eliminadas = resenaRepositorio.deleteByVehiculoMatricula(matricula); // o el método equivalente
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Reseñas eliminadas");
        respuesta.put("total", eliminadas);
        return ResponseEntity.ok(respuesta);
    }
}

    // Otros métodos

