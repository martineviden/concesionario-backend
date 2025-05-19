package com.atos.concesionario.proyecto_concesionario;

import com.atos.concesionario.proyecto_concesionario.Service.*;
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

    @Bean
    public TipoVehiculoServicio tipoVehiculoServicio() {
        return Mockito.mock(TipoVehiculoServicio.class);
    }

}
