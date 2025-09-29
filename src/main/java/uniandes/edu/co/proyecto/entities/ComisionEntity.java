package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comisiones")
public class ComisionEntity {
    
    @Id
    @OneToOne
    private CostoEntity costoTotal;

    private double comision;

    // Constructor
    public ComisionEntity(CostoEntity costoTotal, double comision) {
        this.costoTotal = costoTotal;
        this.comision = comision;
    }

    // Getters y Setters
    public CostoEntity getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(CostoEntity costoTotal) {
        this.costoTotal = costoTotal;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
}
