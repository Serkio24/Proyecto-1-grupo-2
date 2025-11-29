package uniandes.edu.co.proyecto.dto;

public class RFC1DTO {
    private Long idServicio;
    private String fecha;
    private Double longitudTrayecto;
    private Double costoTotal;
    private String tipoServicio;
    private Long idVehiculo;

    public RFC1DTO() {
    }

    public RFC1DTO(Long idServicio, String fecha, Double longitudTrayecto, Double costoTotal, String tipoServicio, Long idVehiculo) {
        this.idServicio = idServicio;
        this.fecha = fecha;
        this.longitudTrayecto = longitudTrayecto;
        this.costoTotal = costoTotal;
        this.tipoServicio = tipoServicio;
        this.idVehiculo = idVehiculo;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getLongitudTrayecto() {
        return longitudTrayecto;
    }

    public void setLongitudTrayecto(Double longitudTrayecto) {
        this.longitudTrayecto = longitudTrayecto;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
