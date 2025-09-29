package uniandes.edu.co.proyecto.entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "conductor_vehiculos")
public class ConductorVehiculoEntity {

    @EmbeddedId
    private ConductorVehiculoPK pk;

    public ConductorVehiculoEntity() { }

    public ConductorVehiculoEntity(UsuarioEntity idConductor, VehiculoEntity idVehiculo) {
        this.pk = new ConductorVehiculoPK(idConductor, idVehiculo);
    }

    public ConductorVehiculoPK getPk() {
        return pk;
    }

    public void setPk(ConductorVehiculoPK pk) {
        this.pk = pk;
    }
}
