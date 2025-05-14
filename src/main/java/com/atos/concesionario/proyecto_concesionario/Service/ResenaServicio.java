package com.atos.concesionario.proyecto_concesionario.Service;

import java.util.*;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Repository.ResenaRepositorio;

@Service
public class ResenaServicio {

    private final ResenaRepositorio resenaRepositorio;

    public ResenaServicio(ResenaRepositorio resenaRepositorio) {
        this.resenaRepositorio = resenaRepositorio;
    }

    public Page<Resena> obtenerResenasPaginadas(int pagina, int tamaño) {
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by("fecha").descending());
        return resenaRepositorio.findAll(pageable);
    }

    public List<Resena> obtenerTodasResenas() {
        return resenaRepositorio.findAll();
    }

    public Resena obtenerResenaPorId(Long id) throws ResourceNotFoundException {
        return resenaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada"));
    }

    public Resena crearResena(Resena resena) {
        return resenaRepositorio.save(resena);
    }

    public Resena actualizarResena(Long id, Resena resenaDetalles) throws ResourceNotFoundException {
        Resena resena = obtenerResenaPorId(id);
        // Actualizar campos
        resena.setTexto(resenaDetalles.getTexto());
        resena.setPuntuacion(resenaDetalles.getPuntuacion());
        return resenaRepositorio.save(resena);
    }

    public void eliminarResena(Long id) throws ResourceNotFoundException {
        Resena resena = obtenerResenaPorId(id);
        resenaRepositorio.delete(resena);
    }
}