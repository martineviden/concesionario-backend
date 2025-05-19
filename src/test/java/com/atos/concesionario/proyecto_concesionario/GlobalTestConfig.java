package com.atos.concesionario.proyecto_concesionario;

import com.atos.concesionario.proyecto_concesionario.Service.ResenaServicio;
import com.atos.concesionario.proyecto_concesionario.Service.ReservaServicio;
import com.atos.concesionario.proyecto_concesionario.Service.UsuarioServicio;
import com.atos.concesionario.proyecto_concesionario.Service.VehiculoServicio;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GlobalTestConfig {

    @Bean
    public UsuarioServicio usuarioServicio() {
        return Mockito.mock(UsuarioServicio.class);
    }

    @Bean
    public VehiculoServicio vehiculoServicio() {
        return Mockito.mock(VehiculoServicio.class);
    }

    @Bean
    public ReservaServicio reservaServicio() {
        return Mockito.mock(ReservaServicio.class);
    }

    @Bean
    public ResenaServicio resenaServicio() {
        return Mockito.mock(ResenaServicio.class);
    }
}
