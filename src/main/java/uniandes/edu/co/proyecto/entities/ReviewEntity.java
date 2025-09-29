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
    @JoinColumn(name = "idUsuarioCalificador", nullable = false)
    private UsuarioEntity usuarioCalificador;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCalificado", nullable = false)
    private UsuarioEntity usuarioCalificado;

    private Double puntuacion;
    private String comentario;
    private LocalDate fechaRevision;

    // Constructor
    public ReviewEntity(UsuarioEntity usuarioCalificador, UsuarioEntity usuarioCalificado, Double puntuacion, String comentario, LocalDate fechaRevision) {
        this.usuarioCalificador = usuarioCalificador;
        this.usuarioCalificado = usuarioCalificado;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaRevision = fechaRevision;
    }

    public ReviewEntity() {}

    // Getters y Setters
    public Long getIdRevision() { return idRevision; }
    public void setIdRevision(Long idRevision) { this.idRevision = idRevision; }

    public UsuarioEntity getUsuarioCalificador() { return usuarioCalificador; }
    public void setUsuarioCalificador(UsuarioEntity usuarioCalificador) { this.usuarioCalificador = usuarioCalificador; }

    public UsuarioEntity getUsuarioCalificado() { return usuarioCalificado; }
    public void setUsuarioCalificado(UsuarioEntity usuarioCalificado) { this.usuarioCalificado = usuarioCalificado; }

    public Double getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Double puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDate getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDate fechaRevision) { this.fechaRevision = fechaRevision; }
}
