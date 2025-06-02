package com.atos.concesionario.proyecto_concesionario.Repository;

import java.util.List;
import java.util.Optional;

import com.atos.concesionario.proyecto_concesionario.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atos.concesionario.proyecto_concesionario.Model.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
    List<Usuario> findByRol(Usuario.Rol rol);
    Optional<Usuario> findByCorreo(String correo);
    Reserva findById(long id);



}
