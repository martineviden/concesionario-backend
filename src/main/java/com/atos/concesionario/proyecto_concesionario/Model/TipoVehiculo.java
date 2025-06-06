package com.atos.concesionario.proyecto_concesionario.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tipo_vehiculo")
@Data
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id"
)
public class TipoVehiculo {
	public enum Tipo {
		MOTO, COCHE, FURGONETA, ELECTRICO, HIBRIDO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String marca;

	@Column(nullable = false)
	private String modelo;

	@Column(nullable = false)
	private double precio;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_vehiculo", nullable = false, length = 20)
	private Tipo tipo;

	@Column(name = "imagen")
	private String imagen;

	@OneToMany(mappedBy = "tipoVehiculo", cascade = CascadeType.ALL)

	private List<Vehiculo> vehiculos;

}
