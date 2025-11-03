package uniandes.edu.co.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="disponibilidades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DisponibilidadEntity {

    @EmbeddedId
    private DisponibilidadPK pk;

    private boolean disponible = true;

    public DisponibilidadEntity() {}

    public DisponibilidadEntity(VehiculoEntity vehiculo, FranjaHorariaEntity franja) {
        this.pk = new DisponibilidadPK(vehiculo, franja);
    }

    // Getters y Setters
    public DisponibilidadPK getPk() { return pk; }
    public void setPk(DisponibilidadPK pk) { this.pk = pk; }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


}
