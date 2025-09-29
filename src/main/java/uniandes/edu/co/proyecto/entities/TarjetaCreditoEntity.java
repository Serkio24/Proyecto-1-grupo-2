package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tarjetas_credito")
public class TarjetaCreditoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTarjetaCredito;
    private String titularDeLaTarjeta;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String codigoSeguridad;

    // Constructor
    public TarjetaCreditoEntity(String titularDeLaTarjeta, String numeroTarjeta, String fechaExpiracion, String codigoSeguridad) {
        this.titularDeLaTarjeta = titularDeLaTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.codigoSeguridad = codigoSeguridad;
    }

    public TarjetaCreditoEntity() {}

    // Getters y Setters
    public Long getIdTarjetaCredito() { return idTarjetaCredito; }
    public void setIdTarjetaCredito(Long idTarjetaCredito) { this.idTarjetaCredito = idTarjetaCredito; }

    public String getTitularDeLaTarjeta() { return titularDeLaTarjeta; }
    public void setTitularDeLaTarjeta(String titularDeLaTarjeta) { this.titularDeLaTarjeta = titularDeLaTarjeta; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }

    public String getCodigoSeguridad() { return codigoSeguridad; }
    public void setCodigoSeguridad(String codigoSeguridad) { this.codigoSeguridad = codigoSeguridad; }
}
