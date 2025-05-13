package com.atos.concesionario.proyecto_concesionario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.Resena;

@Repository
public interface ResenaRepositorio extends JpaRepository<Resena, Long> {
	// Método básico (ya incluido en JpaRepository)
    // List<Resena> findAll(); 
    
    // Métodos personalizados adicionales (si los necesitas)
    List<Resena> findByUsuarioId(Long usuarioId);
    
    List<Resena> findByVehiculoMatricula(String matricula);
    
    List<Resena> findByPuntuacionGreaterThanEqual(int puntuacionMinima);
}
