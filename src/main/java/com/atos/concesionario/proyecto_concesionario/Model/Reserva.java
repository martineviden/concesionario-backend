package com.atos.concesionario.proyecto_concesionario.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "matricula", nullable = false)
    @JsonBackReference("vehiculo-reserva")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference("usuario-reserva")
    private Usuario usuario;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "dias_reserva", nullable = false)
    private Integer diasReserva;

    @Column(nullable = false)
    private Double precio;

}