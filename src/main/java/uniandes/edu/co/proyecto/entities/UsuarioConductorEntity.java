package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_conductores")
public class UsuarioConductorEntity extends UsuarioEntity{

    private String estado;

    // Constructor con parámetros
    public UsuarioConductorEntity(String nombre, String correoElectronico, Long numeroCelular, Long numeroCedula, String estado) {
        super(nombre, correoElectronico, numeroCelular, numeroCedula);
        this.estado = estado;
    }

    // Constructor vacío
    public UsuarioConductorEntity() {}

    // Getters
    public String getEstado() {
        return estado;
    }

    // Setters
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
