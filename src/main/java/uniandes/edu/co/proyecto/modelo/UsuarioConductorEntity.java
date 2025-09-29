package uniandes.edu.co.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_conductores")
public class UsuarioConductorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String estado;

    // Constructor con parámetros
    public UsuarioConductorEntity(String estado) {
        this.estado = estado;
    }

    // Constructor vacío
    public UsuarioConductorEntity() {}

    // Getters
    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
