package uniandes.edu.co.proyecto.dto;

public class ActualizarDisponibilidadDTO {
    private Long idConductor;
    private Long idVehiculoAnt;
    private Long idFranjaAnt;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String tipoServicio;
    private Long idVehiculoNuevo;

    // Getters y setters
    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public Long getIdVehiculoAnt() {
        return idVehiculoAnt;
    }

    public void setIdVehiculoAnt(Long idVehiculoAnt) {
        this.idVehiculoAnt = idVehiculoAnt;
    }

    public Long getIdFranjaAnt() {
        return idFranjaAnt;
    }

    public void setIdFranjaAnt(Long idFranjaAnt) {
        this.idFranjaAnt = idFranjaAnt;
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

    public Long getIdVehiculoNuevo() {
        return idVehiculoNuevo;
    }

    public void setIdVehiculoNuevo(Long idVehiculoNuevo) {
        this.idVehiculoNuevo = idVehiculoNuevo;
    }
}

