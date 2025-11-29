package uniandes.edu.co.proyecto.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "USUARIOS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioEntity {

    @Id
    private Long idUsuario;   // mapea al _id numérico en Mongo (según tu esquema)

    private String nombre;

    private String numeroCelular;

    private String numeroCedula;

    private String correoElectronico;

    // "Cliente" o "Conductor"
    private String tipoUsuario;

    // Tarjetas de crédito embebidas dentro del usuario
    private List<TarjetaCreditoEntity> tarjetasCredito;

    public UsuarioEntity() {
    }

    public UsuarioEntity(String nombre, String numeroCelular, String numeroCedula, String correoElectronico, String tipoUsuario) {
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
        this.numeroCedula = numeroCedula;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipoUsuario;
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<TarjetaCreditoEntity> getTarjetasCredito() {
        return tarjetasCredito;
    }

    public void setTarjetasCredito(List<TarjetaCreditoEntity> tarjetasCredito) {
        this.tarjetasCredito = tarjetasCredito;
    }
}
