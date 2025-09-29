package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="puntos_geograficos")
public class PuntoGeograficoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPunto;

    private String nombre;   // puede ser null (0..1)
    private String direccion;
    private Double latitud;
    private Double longitud;

    // Constructor con parámetros
    public PuntoGeograficoEntity(String nombre, String direccion, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Constructor vacío
    public PuntoGeograficoEntity() {}

    // Getters
    public Long getIdPunto() {
        return idPunto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    // Setters
    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
