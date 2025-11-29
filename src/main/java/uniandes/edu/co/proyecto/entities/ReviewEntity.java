package uniandes.edu.co.proyecto.entities;

import java.util.Date;

public class ReviewEntity {

    private Long idRevision;
    private Long idUsuarioCalificador;
    private Long idUsuarioCalificado;
    private Double puntuacion;
    private String comentario;
    private Date fecha;

    public ReviewEntity() {
    }

    public ReviewEntity(Long idRevision, Long idUsuarioCalificador, Long idUsuarioCalificado, Double puntuacion, String comentario, Date fecha) {
        this.idRevision = idRevision;
        this.idUsuarioCalificador = idUsuarioCalificador;
        this.idUsuarioCalificado = idUsuarioCalificado;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Long getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Long idRevision) {
        this.idRevision = idRevision;
    }

    public Long getIdUsuarioCalificador() {
        return idUsuarioCalificador;
    }

    public void setIdUsuarioCalificador(Long idUsuarioCalificador) {
        this.idUsuarioCalificador = idUsuarioCalificador;
    }

    public Long getIdUsuarioCalificado() {
        return idUsuarioCalificado;
    }

    public void setIdUsuarioCalificado(Long idUsuarioCalificado) {
        this.idUsuarioCalificado = idUsuarioCalificado;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
