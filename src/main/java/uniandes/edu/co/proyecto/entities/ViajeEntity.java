package uniandes.edu.co.proyecto.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="viajes")
public class ViajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idViaje;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double longitudTrayecto;

    @ManyToOne
    @JoinColumn(name="idServicio", referencedColumnName = "idServicio")
    private ServicioEntity idServicio;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="idConductor", referencedColumnName="idConductor"),
        @JoinColumn(name="idVehiculoConductor", referencedColumnName="idVehiculo")
    })
    private UsuarioEntity idConductor;    
    
    @ManyToOne
    @JoinColumn(name="idVehiculo", referencedColumnName = "idVehiculo")
    private VehiculoEntity idVehiculo;

    // Constructor con parámetros
    public ViajeEntity(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Double longitudTrayecto,
                       ServicioEntity idServicio, UsuarioEntity idConductor, VehiculoEntity idVehiculo) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.longitudTrayecto = longitudTrayecto;
        this.idServicio = idServicio;
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
    }

    // Constructor vacío
    public ViajeEntity() {}

    // Getters
    public Long getIdViaje() {
        return idViaje;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Double getLongitudTrayecto() {
        return longitudTrayecto;
    }

    public ServicioEntity getIdServicio() {
        return idServicio;
    }

    public UsuarioEntity getIdConductor() {
        return idConductor;
    }

    public VehiculoEntity getIdVehiculo() {
        return idVehiculo;
    }

    // Setters
    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public void setLongitudTrayecto(Double longitudTrayecto) {
        this.longitudTrayecto = longitudTrayecto;
    }

    public void setIdServicio(ServicioEntity idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdConductor(UsuarioEntity idConductor) {
        this.idConductor = idConductor;
    }

    public void setIdVehiculo(VehiculoEntity idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
