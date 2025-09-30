package uniandes.edu.co.proyecto.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tarjetas_credito")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TarjetaCreditoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTarjetaCredito;

    private String titularDeLaTarjeta;
    private String numeroTarjeta;
    private LocalDate fechaExpiracion; // cambiado a LocalDate
    private Integer codigoSeguridad;

    @ManyToOne
    @JoinColumn(name="clienteId", referencedColumnName="idUsuario")
    private UsuarioEntity cliente;

    public TarjetaCreditoEntity(String titularDeLaTarjeta, String numeroTarjeta, LocalDate fechaExpiracion, Integer codigoSeguridad, UsuarioEntity cliente) {
        this.titularDeLaTarjeta = titularDeLaTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.codigoSeguridad = codigoSeguridad;
        this.cliente = cliente;
    }

    public TarjetaCreditoEntity() {}

    // Getters y Setters
    public Long getIdTarjetaCredito() { return idTarjetaCredito; }
    public void setIdTarjetaCredito(Long idTarjetaCredito) { this.idTarjetaCredito = idTarjetaCredito; }

    public String getTitularDeLaTarjeta() { return titularDeLaTarjeta; }
    public void setTitularDeLaTarjeta(String titularDeLaTarjeta) { this.titularDeLaTarjeta = titularDeLaTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }

    public Integer getCodigoSeguridad() { return codigoSeguridad; }
    public void setCodigoSeguridad(Integer codigoSeguridad) { this.codigoSeguridad = codigoSeguridad; }

    public UsuarioEntity getCliente() { return cliente; }
    public void setCliente(UsuarioEntity cliente) { this.cliente = cliente; }
}
