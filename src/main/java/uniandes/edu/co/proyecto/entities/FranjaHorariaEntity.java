package uniandes.edu.co.proyecto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="franjas_horarias")
public class FranjaHorariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "franjas_horarias_seq_gen")
    @SequenceGenerator(name = "franjas_horarias_seq_gen", sequenceName = "franjas_horarias_SEQ", allocationSize = 1)
    private Long idFranja;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String tipoServicio;

    public FranjaHorariaEntity() {}

    public FranjaHorariaEntity(String diaSemana, String horaInicio, String horaFin, String tipoServicio) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoServicio = tipoServicio;
    }

    // Getters y Setters
    public Long getIdFranja() { return idFranja; }
    public void setIdFranja(Long idFranja) { this.idFranja = idFranja; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
}
