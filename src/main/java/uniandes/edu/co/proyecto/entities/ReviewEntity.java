package uniandes.edu.co.proyecto.entities;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRevision;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCalificador", referencedColumnName="idUsuario")
    private UsuarioEntity usuarioCalificador;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCalificado", referencedColumnName="idUsuario")
    private UsuarioEntity usuarioCalificado;

    //FK hacia viaje
    @ManyToOne
    @JoinColumn(name = "idViaje", referencedColumnName="idViaje")
    private ViajeEntity viaje;

    private Double puntuacion;
    private String comentario;
    private LocalDate fechaRevision;

    // Constructores
    public ReviewEntity() {}

    public ReviewEntity(UsuarioEntity usuarioCalificador, UsuarioEntity usuarioCalificado, ViajeEntity viaje, Double puntuacion, String comentario, LocalDate fechaRevision) {
        this.usuarioCalificador = usuarioCalificador;
        this.usuarioCalificado = usuarioCalificado;
        this.viaje = viaje;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaRevision = fechaRevision;
    }

    // Getters y Setters
    public Long getIdRevision() { return idRevision; }
    public void setIdRevision(Long idRevision) { this.idRevision = idRevision; }

    public UsuarioEntity getUsuarioCalificador() { return usuarioCalificador; }
    public void setUsuarioCalificador(UsuarioEntity usuarioCalificador) { this.usuarioCalificador = usuarioCalificador; }

    public UsuarioEntity getUsuarioCalificado() { return usuarioCalificado; }
    public void setUsuarioCalificado(UsuarioEntity usuarioCalificado) { this.usuarioCalificado = usuarioCalificado; }

    public ViajeEntity getViaje() { return viaje; }
    public void setViaje(ViajeEntity viaje) { this.viaje = viaje; }

    public Double getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Double puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDate getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDate fechaRevision) { this.fechaRevision = fechaRevision; }
}
