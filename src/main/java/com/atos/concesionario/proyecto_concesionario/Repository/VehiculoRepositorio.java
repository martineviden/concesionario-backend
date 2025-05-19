package com.atos.concesionario.proyecto_concesionario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.TipoVehiculo;
import com.atos.concesionario.proyecto_concesionario.Model.Vehiculo;
import java.util.List;


@Repository
public interface VehiculoRepositorio extends JpaRepository<Vehiculo, String> {

    List<Vehiculo> findByDisponibilidad(Boolean disponibilidad);

    List<Vehiculo> findByUbicacion(String ubicacion);

    List<Vehiculo> findByTipoVehiculo(TipoVehiculo.Tipo tipo);

    List<Vehiculo> findByMarcaContainingIgnoreCase(String marca);


}
