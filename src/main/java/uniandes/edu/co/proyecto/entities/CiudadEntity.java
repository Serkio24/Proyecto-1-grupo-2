package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="ciudades")
public class CiudadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ciudades_seq_gen")
    @SequenceGenerator(name = "ciudades_seq_gen", sequenceName = "ciudades_SEQ", allocationSize = 1)
    private Long idCiudad;
    private String nombre;

    public CiudadEntity() {}

    public CiudadEntity(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Long idCiudad) { this.idCiudad = idCiudad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
