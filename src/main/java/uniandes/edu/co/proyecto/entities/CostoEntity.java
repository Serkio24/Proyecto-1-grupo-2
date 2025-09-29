package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="costos")
public class CostoEntity {
    
    @EmbeddedId
    private CostoPK pk;
    private double costoTotal;

    public CostoEntity(ViajeEntity longitudTrayecto, TarifaEntity idTarifa, double costoTotal) {
        this.pk = new CostoPK(longitudTrayecto, idTarifa);
        this.costoTotal = costoTotal;
    }
    public CostoEntity() {;}
    public CostoPK getPk() {
        return pk;
    }
    public void setPk(CostoPK pk) {
        this.pk = pk;
    }
    public double getCostoTotal() {
        return costoTotal;
    }
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
    
}
