package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios_conductor")
public class UsuarioConductorEntity {

    @EmbeddedId
    private UsuarioConductorPK pk;

    public UsuarioConductorEntity() {}

    public UsuarioConductorEntity(UsuarioEntity conductor, VehiculoEntity vehiculo) {
        this.pk = new UsuarioConductorPK(conductor, vehiculo);
    }

    // Getters y Setters
    public UsuarioConductorPK getPk() { return pk; }
    public void setPk(UsuarioConductorPK pk) { this.pk = pk; }
}
