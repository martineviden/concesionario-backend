package com.atos.concesionario.proyecto_concesionario.Model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehiculo")
@Data
public class Vehiculo {

    public enum Combustible {
        GASOLINA, DIESEL, GLP, ELECTRICO
    }

    public enum Transmision {
        MANUAL, AUTOMATICO
    }

    public enum EtiquetaAmbiental {
        CERO, ECO, C, B, A;

        public String getValue() {
            return this.name();
        }
    }

    public enum Provincia {
        ALAVA, ALBACETE, ALICANTE, ALMERIA, ASTURIAS,
        AVILA, BADAJOZ, BARCELONA, BURGOS, CACERES,
        CADIZ, CANTABRIA, CASTELLON, CIUDADREAL, CORDOBA,
        LACORUNA, CUENCA, GERONA, GRANADA, GUADALAJARA,
        GUIPUZCOA, HUELVA, HUESCA, BALEARES, JAEN,
        LEON, LERIDA, LUGO, MADRID, MALAGA,
        MURCIA, NAVARRA, ORENSE, PALENCIA, LASPALMAS,
        PONTEVEDRA, LARIOJA, SALAMANCA, SEGOVIA, SEVILLA,
        SORIA, TARRAGONA, SANTACRUZDETENERIFE, TERUEL, TOLEDO,
        VALENCIA, VALLADOLID, VIZCAYA, ZAMORA, ZARAGOZA
    }

    @Id
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "id_tipo_vehiculo", nullable = false)
    @JsonIgnoreProperties("vehiculos") // ← evita bucle infinito
    private TipoVehiculo tipoVehiculo;

    // Campos generales
    @Column(nullable = false)
    private String color;

    private String marca;

    private Integer kilometraje;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion", nullable = false)
    private Provincia ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "combustible")
    private Combustible combustible;

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
