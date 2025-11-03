package uniandes.edu.co.proyecto.dto;

import java.util.List;

public class SolicitudServicioDTO {
    private Long idUsuario;
    private String tipoServicio;
    private String nivelRequerido;
    private Long idPuntoPartida;
    private List<Long> destinos;
    private String orden;
    private String restaurante;

    // Getters y setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getNivelRequerido() { return nivelRequerido; }
    public void setNivelRequerido(String nivelRequerido) { this.nivelRequerido = nivelRequerido; }

    public Long getIdPuntoPartida() { return idPuntoPartida; }
    public void setIdPuntoPartida(Long idPuntoPartida) { this.idPuntoPartida = idPuntoPartida; }
    public List<Long> getDestinos() {
        return destinos;
    }
    public void setDestinos(List<Long> destinos) {
        this.destinos = destinos;
    }
    public String getOrden() {
        return orden;
    }
    public void setOrden(String orden) {
        this.orden = orden;
    }
    public String getRestaurante() {
        return restaurante;
    }
    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    
}
