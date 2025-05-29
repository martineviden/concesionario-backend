package com.atos.concesionario.proyecto_concesionario.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    public enum Rol {
        CLIENTE, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni; // Debería almacenarse cifrado

    @Column(nullable = false)
    private String nombre;

    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    @Getter(onMethod = @__({@JsonIgnore}))
    private String contrasena;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @JsonManagedReference("usuario-reserva")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reserva> reservas = List.of();

    @JsonManagedReference("usuario-resena")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Resena> resenas = List.of();

}