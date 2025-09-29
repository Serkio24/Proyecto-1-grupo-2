package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class UsuarioConductorPK implements Serializable {

    @ManyToOne
    @JoinColumn(name="idConductor", referencedColumnName="idUsuario")
    private UsuarioEntity conductor;

    @ManyToOne
    @JoinColumn(name="idVehiculo", referencedColumnName="idVehiculo")
    private VehiculoEntity vehiculo;

    public UsuarioConductorPK() {}

    public UsuarioConductorPK(UsuarioEntity conductor, VehiculoEntity vehiculo) {
        this.conductor = conductor;
        this.vehiculo = vehiculo;
    }

    // Getters y Setters
    public UsuarioEntity getConductor() { return conductor; }
    public void setConductor(UsuarioEntity conductor) { this.conductor = conductor; }

    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
}
