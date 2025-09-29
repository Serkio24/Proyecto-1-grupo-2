package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="servicio_destinos")
public class ServicioDestinoEntity {
    @Id
    @OneToOne
    @JoinColumn(name="idServicio", referencedColumnName = "idServicio")
    private ServicioEntity idServicio;

    @ManyToOne
    @JoinColumn(name="idPuntoLlegada", referencedColumnName = "idPunto")
    private PuntoGeograficoEntity idPuntoLlegada;

    // Constructor con parámetros
    public ServicioDestinoEntity(ServicioEntity idServicio, PuntoGeograficoEntity idPuntoLlegada) {
        this.idServicio = idServicio;
        this.idPuntoLlegada = idPuntoLlegada;
    }

    // Constructor vacío
    public ServicioDestinoEntity() {}

    // Getters
    public ServicioEntity getIdServicio() {
        return idServicio;
    }

    public PuntoGeograficoEntity getIdPuntoLlegada() {
        return idPuntoLlegada;
    }

    // Setters
    public void setIdServicio(ServicioEntity idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdPuntoLlegada(PuntoGeograficoEntity idPuntoLlegada) {
        this.idPuntoLlegada = idPuntoLlegada;
    }
}
