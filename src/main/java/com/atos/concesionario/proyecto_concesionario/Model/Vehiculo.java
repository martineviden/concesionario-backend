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
	    CERO, ECO, C, B, A;
	    
	    public String getValue() {
	        return this.name(); // Devuelve "CERO", "ECO", etc.
	    }
	}

	@Id
	@Column(name = "matricula", nullable = false, unique = true)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_vehiculo", nullable = false)
	private TipoVehiculo tipoVehiculo;

	// Campos generales
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

	
	 // Campos condicionales según tipo
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
	
	// Método helper para verificar tipo
    public boolean esMoto() {
        return tipoVehiculo.getTipo() == TipoVehiculo.Tipo.MOTO;
    }

    // Validación automática
    @PrePersist
    @PreUpdate
    private void validate() {
        if (esMoto() && puertas != null) {
            throw new IllegalStateException("Las motos no tienen puertas");
        }
        
        if (!esMoto() && tipoVehiculo.getTipo() == TipoVehiculo.Tipo.COCHE && puertas == null) {
            throw new IllegalStateException("Los coches deben especificar número de puertas");
        }
    }

}
