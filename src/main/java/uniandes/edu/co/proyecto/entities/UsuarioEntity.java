package uniandes.edu.co.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;

    private String nombre;

    private String numeroCelular;

    private String numeroCedula;

    private String correoElectronico;

    private String tipoUsuario;

    public UsuarioEntity() { }

    public UsuarioEntity(String nombre, String numeroCelular, String numeroCedula, String correoElectronico, String tipo) {
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
        this.numeroCedula = numeroCedula;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTipo() {
        return tipoUsuario;
    }

    public void setTipo(String tipo) {
        this.tipoUsuario = tipo;
    }
}
