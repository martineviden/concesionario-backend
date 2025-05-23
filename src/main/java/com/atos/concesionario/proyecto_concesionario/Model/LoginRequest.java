package com.atos.concesionario.proyecto_concesionario.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String correo;
    private String contrasena;
}
