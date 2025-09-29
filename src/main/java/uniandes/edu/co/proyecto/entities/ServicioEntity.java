package uniandes.edu.co.proyecto.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="servicios")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idServicio;

    @ManyToOne
    @JoinColumn(name="idCliente", referencedColumnName="idUsuario")
    private UsuarioEntity idCliente;

    private LocalDateTime fechaHora;
    private String tipoServicio;
    private String nivelRequerido;
    private String estado; // Pendiente, Asignado, Cancelado
    private String orden;      // [0..1]
    private String restaurante; // [0..1]

    @ManyToOne
    @JoinColumn(name="idPuntoPartida", referencedColumnName = "idPunto")
    private PuntoGeograficoEntity idPuntoPartida;

    //------- acá son los atributos viejitos 
    // private Double longitudTrayecto;
    // private Double costoTotal;
    // private Double comision;
    // private String nivel;      // [0..1]

    // Constructor con parámetros
    public ServicioEntity(UsuarioEntity idCliente, LocalDateTime fechaHora, String tipoServicio,
                          String nivelRequerido, String estado, String orden,
                          String restaurante, PuntoGeograficoEntity idPuntoPartida) {
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.tipoServicio = tipoServicio;
        this.nivelRequerido = nivelRequerido;
        this.estado = estado;
        this.orden = orden;
        this.restaurante = restaurante;
        this.idPuntoPartida = idPuntoPartida;
    }

    // Constructor vacío
    public ServicioEntity() {}

    // Getters
    public Long getIdServicio() {
        return idServicio;
    }

    public UsuarioEntity getIdCliente() {
        return idCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public String getNivelRequerido() {
        return nivelRequerido;
    }

    public String getEstado() {
        return estado;
    }

    public String getOrden() {
        return orden;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public PuntoGeograficoEntity getIdPuntoPartida() {
        return idPuntoPartida;
    }

    // Setters
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public void setIdCliente(UsuarioEntity idCliente) {
        this.idCliente = idCliente;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setNivelRequerido(String nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public void setIdPuntoPartida(PuntoGeograficoEntity idPuntoPartida) {
        this.idPuntoPartida = idPuntoPartida;
    }
}
