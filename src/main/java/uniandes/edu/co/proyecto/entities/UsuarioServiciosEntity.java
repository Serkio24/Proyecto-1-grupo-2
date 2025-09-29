package uniandes.edu.co.proyecto.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_servicios")
public class UsuarioServiciosEntity extends UsuarioEntity{

    private Long numeroTarjeta;
    private String nombreTarjeta;
    private LocalDate fechaVencimiento;
    private Long codigoSeguridad;

    // Constructor con parámetros
    public UsuarioServiciosEntity(String nombre, String correoElectronico, Long numeroCelular, Long numeroCedula, Long numeroTarjeta, String nombreTarjeta, LocalDate fechaVencimiento, Long codigoSeguridad) {
        super(nombre, correoElectronico, numeroCelular, numeroCedula);
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTarjeta = nombreTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoSeguridad = codigoSeguridad;
    }

    // Constructor vacío
    public UsuarioServiciosEntity() {}

    // Getters
    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Long getCodigoSeguridad() {
        return codigoSeguridad;
    }

    //Setters
    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCodigoSeguridad(Long codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
    
}