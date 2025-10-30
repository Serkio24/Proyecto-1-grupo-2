package uniandes.edu.co.proyecto.dto;

public class RFC3DTO {
    private Long idVehiculo;
    private String tipoServicio;
    private Long idUsuario;
    private Double costoTotal;
    private Double ganancia;

    public RFC3DTO() {
    }

    public RFC3DTO(Long idVehiculo, String tipoServicio, Long idUsuario, Double costoTotal, Double ganancia) {
        this.idVehiculo = idVehiculo;
        this.tipoServicio = tipoServicio;
        this.idUsuario = idUsuario;
        this.costoTotal = costoTotal;
        this.ganancia = ganancia;
    }

    
    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Double getGanancia() {
        return ganancia;
    }

    public void setGanancia(Double ganancia) {
        this.ganancia = ganancia;
    }
}
