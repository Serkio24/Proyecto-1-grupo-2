package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;

    private String nombre;
    private String correoElectronico;
    private Long numeroCelular;
    private Long numeroCedula;

    // Constructor con parámetros
    public UsuarioEntity(String nombre, String correoElectronico, Long numeroCelular, Long numeroCedula) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.numeroCelular = numeroCelular;
        this.numeroCedula = numeroCedula;
    }

    // Constructor vacío
    public UsuarioEntity() {}

    // Getters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public Long getNumeroCelular() {
        return numeroCelular;
    }

    public Long getNumeroCedula() {
        return numeroCedula;
    }

    // Setters
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNumeroCelular(Long numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public void setNumeroCedula(Long numeroCedula) {
        this.numeroCedula = numeroCedula;
    }
}
