package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConductorVehiculoPK implements Serializable {

    private Long idConductor;
    private Long idVehiculo;

    public ConductorVehiculoPK() {}

    public ConductorVehiculoPK(Long idConductor, Long idVehiculo) {
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
    }

    // equals y hashCode son obligatorios
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConductorVehiculoPK)) return false;
        ConductorVehiculoPK that = (ConductorVehiculoPK) o;
        return Objects.equals(idConductor, that.idConductor) &&
               Objects.equals(idVehiculo, that.idVehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConductor, idVehiculo);
    }

    // Getters y setters
    public Long getIdConductor() { return idConductor; }
    public void setIdConductor(Long idConductor) { this.idConductor = idConductor; }

    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long idVehiculo) { this.idVehiculo = idVehiculo; }
}
