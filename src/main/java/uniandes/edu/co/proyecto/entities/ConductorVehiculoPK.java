package uniandes.edu.co.proyecto.entities;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ConductorVehiculoPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idConductor", referencedColumnName = "idUsuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuarioEntity idConductor;

    @ManyToOne
    @JoinColumn(name = "idVehiculo", referencedColumnName = "idVehiculo")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private VehiculoEntity idVehiculo;

    public ConductorVehiculoPK() { }

    public ConductorVehiculoPK(UsuarioEntity idConductor, VehiculoEntity idVehiculo) {
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
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
