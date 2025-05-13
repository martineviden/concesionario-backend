package com.atos.concesionario.proyecto_concesionario.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.Reserva;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
	 // Métodos personalizados útiles
    List<Reserva> findByUsuarioId(Long usuarioId);
    
    List<Reserva> findByVehiculoMatricula(String matricula);
    
    List<Reserva> findByFechaReservaBetween(LocalDate inicio, LocalDate fin);
    
    List<Reserva> findByFechaReservaAfter(LocalDate fecha);
}
