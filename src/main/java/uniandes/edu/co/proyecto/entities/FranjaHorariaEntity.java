package uniandes.edu.co.proyecto.entities;

public class FranjaHorariaEntity {

    private Long idFranja;
    private String diaSemana;
    private String horaInicio;   // "HH:mm"
    private String horaFin;      // "HH:mm"
    private String tipoServicio;
    private Boolean disponible;  // ahora la disponibilidad es por franja

    public FranjaHorariaEntity() {
    }

    public FranjaHorariaEntity(Long idFranja, String diaSemana, String horaInicio, String horaFin, String tipoServicio, Boolean disponible) {
        this.idFranja = idFranja;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoServicio = tipoServicio;
        this.disponible = disponible;
    }

    public Long getIdFranja() {
        return idFranja;
    }

    public void setIdFranja(Long idFranja) {
        this.idFranja = idFranja;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
