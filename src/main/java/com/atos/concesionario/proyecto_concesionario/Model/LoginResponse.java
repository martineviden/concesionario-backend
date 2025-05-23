package com.atos.concesionario.proyecto_concesionario.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private boolean autenticado;
    private Usuario usuario;
}
