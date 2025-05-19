package com.atos.concesionario.proyecto_concesionario.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;

import java.util.List;

@Repository
public interface TipoVehiculoRepositorio extends JpaRepository<TipoVehiculo, Long> {


}
