package com.atos.concesionario.proyecto_concesionario.Config;


import com.atos.concesionario.proyecto_concesionario.Model.Usuario;
import com.atos.concesionario.proyecto_concesionario.Repository.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioInit {

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        return args -> {
            String correoAdmin = "admin@admin.com";

            System.out.println("Correo Admin: " + correoAdmin);
            if (usuarioRepositorio.findByCorreo(correoAdmin).isEmpty()) {
                Usuario admin = Usuario.builder()
                        .nombre("Admin")
                        .apellidos("Admin")
                        .correo(correoAdmin)
                        .dni(passwordEncoder.encode("00000000A"))
                        .telefono("000000000")
                        .contrasena(passwordEncoder.encode("admin"))
                        .rol(Usuario.Rol.ADMIN)
                        .build();

                usuarioRepositorio.save(admin);
                System.out.println("✅ Usuario ADMIN creado correctamente");
            } else {
                System.out.println("🔁 Usuario admin@admin.com ya existe, no se volvió a crear");
            }
        };
    }
}