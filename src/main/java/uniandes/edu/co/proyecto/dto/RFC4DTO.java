package uniandes.edu.co.proyecto.dto;

public class RFC4DTO {
    private String ciudad;
    private String tipoServicio;
    private String nivelRequerido;
    private Long totalServicios;
    private Double porcentaje;

    public RFC4DTO() {
    }

    public RFC4DTO(String ciudad, String tipoServicio, String nivelRequerido, Long totalServicios, Double porcentaje) {
        this.ciudad = ciudad;
        this.tipoServicio = tipoServicio;
        this.nivelRequerido = nivelRequerido;
        this.totalServicios = totalServicios;
        this.porcentaje = porcentaje;
    }

    
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNivelRequerido() {
        return nivelRequerido;
    }

    public void setNivelRequerido(String nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }

    public Long getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(Long totalServicios) {
        this.totalServicios = totalServicios;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
