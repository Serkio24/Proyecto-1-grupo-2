package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="puntos_geograficos")
public class PuntoGeograficoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "puntos_geograficos_seq_gen")
    @SequenceGenerator(name = "puntos_geograficos_seq_gen", sequenceName = "puntos_geograficos_SEQ", allocationSize = 1)
    private Long idPunto;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private String direccionAproximada;


    // FK hacia Ciudad
    @ManyToOne
    @JoinColumn(name="idCiudad", referencedColumnName="idCiudad")
    private CiudadEntity ciudad;

    public PuntoGeograficoEntity() {}

    public PuntoGeograficoEntity(String nombre, Double latitud, Double longitud, String direccionAproximada) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccionAproximada = direccionAproximada;
    }

    // Getters y Setters
    public Long getIdPunto() { return idPunto; }
    public void setIdPunto(Long idPunto) { this.idPunto = idPunto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getDireccionAproximada() { return direccionAproximada; }
    public void setDireccionAproximada(String direccionAproximada) { this.direccionAproximada = direccionAproximada; }

    public CiudadEntity getCiudad() { return ciudad; }
    public void setCiudad(CiudadEntity ciudad) { this.ciudad = ciudad; }
}
