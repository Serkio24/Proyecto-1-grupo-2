package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ConductorVehiculo")
public class ConductorVehiculoEntity {

    @EmbeddedId
    private ConductorVehiculoPK id;

    @ManyToOne
    @MapsId("idConductor") // indica que esta columna es parte de la PK
    @JoinColumn(name = "idConductor", nullable = false)
    private UsuarioEntity conductor;

    @ManyToOne
    @MapsId("idVehiculo") // indica que esta columna es parte de la PK
    @JoinColumn(name = "idVehiculo", nullable = false)
    private VehiculoEntity vehiculo;

    public ConductorVehiculoEntity() {}

    public ConductorVehiculoEntity(UsuarioEntity conductor, VehiculoEntity vehiculo) {
        this.conductor = conductor;
        this.vehiculo = vehiculo;
        this.id = new ConductorVehiculoPK(conductor.getIdUsuario(), vehiculo.getIdVehiculo());
    }

    // Getters y setters
    public ConductorVehiculoPK getId() { return id; }
    public void setId(ConductorVehiculoPK id) { this.id = id; }

    public UsuarioEntity getConductor() { return conductor; }
    public void setConductor(UsuarioEntity conductor) { this.conductor = conductor; }

    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
}
