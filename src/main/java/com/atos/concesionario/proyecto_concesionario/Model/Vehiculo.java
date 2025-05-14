package com.atos.concesionario.proyecto_concesionario.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculo")
@Data
public class Vehiculo {

	public enum Transmision {
		MANUAL, AUTOMATICO
	}

	public enum EtiquetaAmbiental {
		CERO("0"), ECO, C, B, A;

		private String value;

		EtiquetaAmbiental() {
		}

		EtiquetaAmbiental(String value) {
			this.value = value;
		}

		public String getValue() {
			return value != null ? value : this.name();
		}
	}

	@Id
	@Column(name = "matricula", nullable = false, unique = true)
	private String matricula;

	@ManyToOne
	@JoinColumn(name = "id_tipo_vehiculo", nullable = false)
	private TipoVehiculo tipoVehiculo;

	@Column(nullable = false)
	private String color;

	private Integer kilometraje;

	@Column(name = "disponibilidad")
	private Boolean disponibilidad;

	@Column(name = "combustible")
	private String combustible;

	@Enumerated(EnumType.STRING)
	@Column(name = "etiqueta")
	private EtiquetaAmbiental etiqueta;

	@Column(name = "autonomia")
	private Integer autonomia;

	@Column(name = "puertas")
	private Integer puertas;

	@Column(name = "aire_acondicionado")
	private Boolean aireAcondicionado;

	@Column(name = "plazas")
	private Integer plazas;

	@Enumerated(EnumType.STRING)
	@Column(name = "transmision")
	private Transmision transmision;

	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
	private List<Reserva> reservas;

}
