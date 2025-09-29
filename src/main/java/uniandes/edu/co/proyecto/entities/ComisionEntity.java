package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comisiones")
public class ComisionEntity {

    @Id
    private Long costoTotal; // PK y también FK

    @OneToOne
    @MapsId
    @JoinColumn(name = "costoTotal", referencedColumnName = "costoTotal")
    private CostoEntity costoEntity;

    private double comision;

    // Constructores
    public ComisionEntity() {}

    public ComisionEntity(CostoEntity costoEntity, double comision) {
        this.costoEntity = costoEntity;
        this.comision = comision;
    }

    // Getters y Setters
    public Long getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Long costoTotal) {
        this.costoTotal = costoTotal;
    }

    public CostoEntity getCostoEntity() {
        return costoEntity;
    }

    public void setCostoEntity(CostoEntity costoEntity) {
        this.costoEntity = costoEntity;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
}
