package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CostoPK implements Serializable {
    @ManyToOne
    @JoinColumn(name="idViaje", referencedColumnName = "idViaje")
    private ViajeEntity longitudTrayecto;

    @ManyToOne
    @JoinColumn(name="idTarifa", referencedColumnName = "idTarifa")
    private TarifaEntity idTarifa;

    public CostoPK(ViajeEntity longitudTrayecto, TarifaEntity idTarifa) {
        super();
        this.longitudTrayecto = longitudTrayecto;
        this.idTarifa = idTarifa;
    }

    public ViajeEntity getLongitudTrayecto() {
        return longitudTrayecto;
    }

    public void setLongitudTrayecto(ViajeEntity longitudTrayecto) {
        this.longitudTrayecto = longitudTrayecto;
    }

    public TarifaEntity getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(TarifaEntity idTarifa) {
        this.idTarifa = idTarifa;
    }
    
}