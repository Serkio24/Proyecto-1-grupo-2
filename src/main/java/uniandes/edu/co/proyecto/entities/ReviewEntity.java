package uniandes.edu.co.proyecto.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq_gen")
    @SequenceGenerator(name = "reviews_seq_gen", sequenceName = "reviews_SEQ", allocationSize = 1)
    private Long idRevision;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCalificador", referencedColumnName="idUsuario")
    private UsuarioEntity usuarioCalificador;

    @ManyToOne
    @JoinColumn(name = "idUsuarioCalificado", referencedColumnName="idUsuario")
    private UsuarioEntity usuarioCalificado;

    @ManyToOne
    @JoinColumn(name = "idViaje", referencedColumnName="idViaje")
    private ViajeEntity viaje;

    private Double puntuacion;
    private String comentario;
    private LocalDateTime fechaRevision;

    // Constructores
    public ReviewEntity() {}

    public ReviewEntity(UsuarioEntity usuarioCalificador, UsuarioEntity usuarioCalificado, 
                        ViajeEntity viaje, Double puntuacion, String comentario, 
                        LocalDateTime fechaRevision) {
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

    public LocalDateTime getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDateTime fechaRevision) { this.fechaRevision = fechaRevision; }
}
