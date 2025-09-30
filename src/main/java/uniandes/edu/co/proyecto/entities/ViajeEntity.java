package uniandes.edu.co.proyecto.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "viajes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idViaje;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double longitudTrayecto;

    // FK a ServicioEntity
    @ManyToOne
    @JoinColumn(name = "idServicio", referencedColumnName = "idServicio")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ServicioEntity idServicio;

    // FK a UsuarioEntity (conductor)
    @ManyToOne
    @JoinColumn(name = "idConductor", referencedColumnName = "idUsuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuarioEntity idConductor;

    // FK a VehiculoEntity
    @ManyToOne
    @JoinColumn(name = "idVehiculo", referencedColumnName = "idVehiculo")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private VehiculoEntity idVehiculo;

    // Constructor vacío
    public ViajeEntity() {}

    // Constructor con parámetros
    public ViajeEntity(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, double longitudTrayecto,
                       ServicioEntity idServicio, UsuarioEntity idConductor, VehiculoEntity idVehiculo) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.longitudTrayecto = longitudTrayecto;
        this.idServicio = idServicio;
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
    }

    // Getters y Setters
    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public double getLongitudTrayecto() {
        return longitudTrayecto;
    }

    public void setLongitudTrayecto(double longitudTrayecto) {
        this.longitudTrayecto = longitudTrayecto;
    }

    public ServicioEntity getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServicioEntity idServicio) {
        this.idServicio = idServicio;
    }

    public UsuarioEntity getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(UsuarioEntity idConductor) {
        this.idConductor = idConductor;
    }

    public VehiculoEntity getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(VehiculoEntity idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
