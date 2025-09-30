package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="servicios_destinos")
public class ServicioDestinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDestino;

    @ManyToOne
    @JoinColumn(name="idServicio", referencedColumnName = "idServicio", nullable = false)
    private ServicioEntity idServicio;

    @ManyToOne
    @JoinColumn(name="idPuntoLlegada", referencedColumnName = "idPunto", nullable = false)
    private PuntoGeograficoEntity idPuntoLlegada;

    // Constructor con parámetros
    public ServicioDestinoEntity(ServicioEntity idServicio, PuntoGeograficoEntity idPuntoLlegada) {
        this.idServicio = idServicio;
        this.idPuntoLlegada = idPuntoLlegada;
    }

    // Constructor vacío
    public ServicioDestinoEntity() {}

    // Getters
    public Long getIdDestino() {
        return idDestino;
    }

    public ServicioEntity getIdServicio() {
        return idServicio;
    }

    public PuntoGeograficoEntity getIdPuntoLlegada() {
        return idPuntoLlegada;
    }

    // Setters
    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public void setIdServicio(ServicioEntity idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdPuntoLlegada(PuntoGeograficoEntity idPuntoLlegada) {
        this.idPuntoLlegada = idPuntoLlegada;
    }
}
