package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class NivelVehiculoPK implements Serializable {

    @ManyToOne //para mi que eso seria many to many pero no se como ponerlo
    @JoinColumn(name="modelo", referencedColumnName="modelo")
    private VehiculoEntity vehiculoModelo;

    @ManyToOne
    @JoinColumn(name="capacidadPasajeros", referencedColumnName="capacidadPasajeros")
    private VehiculoEntity vehiculoCapacidad;

    public NivelVehiculoPK() {}

    public NivelVehiculoPK(VehiculoEntity vehiculoModelo, VehiculoEntity vehiculoCapacidad) {
        this.vehiculoModelo = vehiculoModelo;
        this.vehiculoCapacidad = vehiculoCapacidad;
    }

    // Getters y Setters
    public VehiculoEntity getVehiculoModelo() { return vehiculoModelo; }
    public void setVehiculoModelo(VehiculoEntity vehiculoModelo) { this.vehiculoModelo = vehiculoModelo; }

    public VehiculoEntity getVehiculoCapacidad() { return vehiculoCapacidad; }
    public void setVehiculoCapacidad(VehiculoEntity vehiculoCapacidad) { this.vehiculoCapacidad = vehiculoCapacidad; }
}
