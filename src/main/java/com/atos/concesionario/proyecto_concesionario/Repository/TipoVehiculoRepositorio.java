package com.atos.concesionario.proyecto_concesionario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;

@Repository
public interface TipoVehiculoRepositorio extends JpaRepository<TipoVehiculo, Long> {
    
}
