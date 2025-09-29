package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ciudades")
public class CiudadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCiudad;

    private String nombre;

    // Constructor con parámetros
    public CiudadEntity(String nombre) {
        this.nombre = nombre;
    }

    // Constructor vacío
    public CiudadEntity() {}

    // Getters
    public Long getIdCiudad() {
        return idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
