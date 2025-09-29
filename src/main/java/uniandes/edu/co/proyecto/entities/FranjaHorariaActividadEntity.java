package uniandes.edu.co.proyecto.entities;

import java.time.LocalTime;
import java.time.DayOfWeek;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="franjas_horarias")
public class FranjaHorariaActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFranjaHoraria;

    private DayOfWeek diaSemana;  // Se puede usar String si prefieres
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String tipoServicio;

    // Constructor con parámetros
    public FranjaHorariaActividadEntity(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin, String tipoServicio) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoServicio = tipoServicio;
    }

    // Constructor vacío
    public FranjaHorariaActividadEntity() {}

    // Getters
    public Long getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    // Setters
    public void setIdFranjaHoraria(Long idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}
