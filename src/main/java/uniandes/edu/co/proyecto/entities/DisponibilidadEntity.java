package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="disponibilidades")
public class DisponibilidadEntity {

    @EmbeddedId
    private DisponibilidadPK pk;

    public DisponibilidadEntity() {}

    public DisponibilidadEntity(VehiculoEntity vehiculo, FranjaHorariaEntity franja) {
        this.pk = new DisponibilidadPK(vehiculo, franja);
    }

    // Getters y Setters
    public DisponibilidadPK getPk() { return pk; }
    public void setPk(DisponibilidadPK pk) { this.pk = pk; }
}
