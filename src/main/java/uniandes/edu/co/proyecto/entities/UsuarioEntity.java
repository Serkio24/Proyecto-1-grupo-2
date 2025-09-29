package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario")
    private Long idUsuario;
    private String nombre;
    private String numeroCelular;
    private String numeroCedula;
    private String correoElectronico;
    private String tipoUsuario; // "Cliente" o "Conductor"

    // Constructor completo
    public UsuarioEntity(String nombre, String numeroCelular, String numeroCedula, String correoElectronico, String tipoUsuario) {
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
        this.numeroCedula = numeroCedula;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipoUsuario;
    }

    // Constructor vacío
    public UsuarioEntity() {}

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumeroCelular() { return numeroCelular; }
    public void setNumeroCelular(String numeroCelular) { this.numeroCelular = numeroCelular; }

    public String getNumeroCedula() { return numeroCedula; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
