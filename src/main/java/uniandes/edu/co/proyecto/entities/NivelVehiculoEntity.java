package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="niveles_vehiculo")
public class NivelVehiculoEntity {

    @EmbeddedId
    private NivelVehiculoPK pk;

    private String nivel;

    public NivelVehiculoEntity() {}

    public NivelVehiculoEntity(NivelVehiculoPK pk, String nivel) {
        this.pk = pk;
        this.nivel = nivel;
    }

    // Getters y setters
    public NivelVehiculoPK getPk() { return pk; }
    public void setPk(NivelVehiculoPK pk) { this.pk = pk; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

}
