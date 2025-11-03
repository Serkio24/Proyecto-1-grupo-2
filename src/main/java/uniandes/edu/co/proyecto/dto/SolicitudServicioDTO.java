package uniandes.edu.co.proyecto.dto;

public class SolicitudServicioDTO {
    private Long idUsuario;
    private String tipoServicio;
    private String nivelRequerido;
    private Long idPuntoPartida;
    private Long idPuntoDestino;

    // Getters y setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getNivelRequerido() { return nivelRequerido; }
    public void setNivelRequerido(String nivelRequerido) { this.nivelRequerido = nivelRequerido; }

    public Long getIdPuntoPartida() { return idPuntoPartida; }
    public void setIdPuntoPartida(Long idPuntoPartida) { this.idPuntoPartida = idPuntoPartida; }

    public Long getIdPuntoDestino() { return idPuntoDestino; }
    public void setIdPuntoDestino(Long idPuntoDestino) { this.idPuntoDestino = idPuntoDestino; }
}
