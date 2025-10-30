package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class DisponibilidadPK implements Serializable {

    @ManyToOne
    @JoinColumn(name="idVehiculo", referencedColumnName="idVehiculo")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private VehiculoEntity vehiculo;

    @ManyToOne
    @JoinColumn(name="idFranja", referencedColumnName="idFranja")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FranjaHorariaEntity franja;

    public DisponibilidadPK() {}

    public DisponibilidadPK(VehiculoEntity vehiculo, FranjaHorariaEntity franja) {
        super();
        this.vehiculo = vehiculo;
        this.franja = franja;
    }

    // Getters y Setters
    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }

    public FranjaHorariaEntity getFranja() { return franja; }
    public void setFranja(FranjaHorariaEntity franja) { this.franja = franja; }
}
