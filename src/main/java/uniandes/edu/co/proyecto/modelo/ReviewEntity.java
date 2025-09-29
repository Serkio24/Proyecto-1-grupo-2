package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double puntuacion;
    private String comentario;
    private LocalDate fechaRevision;

    //Constructor
    public ReviewEntity(Double puntuacion, String comentario, LocalDate fechaRevision){
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fechaRevision = fechaRevision;
    }

    public ReviewEntity() {}

    //Getters
    public Long getId() {
        return id;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFechaRevision(LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

}
