package uniandes.edu.co.proyecto.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "VIAJES")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViajeEntity {

    @Id
    private Long idViaje;            // mapea al _id de Mongo

    // Identificadores de otras entidades (referencias por ID, no por objeto)
    private Long idServicio;
    private Long idCliente;
    private Long idConductor;
    private Long idVehiculo;
    private Long idTarifa;

    // Datos del servicio / solicitud
    private Date fechaHora;          // fecha/hora de solicitud del viaje
    private String tipoServicio;
    private String nivelRequerido;
    private String estado;
    private Integer orden;           // puede ser null
    private String restaurante;      // puede ser null

    // Origen y destinos
    private PuntoGeograficoEntity puntoOrigen;
    private List<PuntoGeograficoEntity> destinos;

    // Tiempos y métricas del viaje
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Double longitudTrayecto;
    private Double costoTotal;

    // Reviews embebidas
    private List<ReviewEntity> reviews;

    public ViajeEntity() {
    }

    public ViajeEntity(Long idServicio, Long idCliente, Long idConductor, Long idVehiculo, Long idTarifa, Date fechaHora, String tipoServicio, String nivelRequerido, String estado, PuntoGeograficoEntity puntoOrigen, List<PuntoGeograficoEntity> destinos, Date fechaHoraInicio, Date fechaHoraFin, Double longitudTrayecto, Double costoTotal) {
        this.idServicio = idServicio;
        this.idCliente = idCliente;
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
        this.idTarifa = idTarifa;
        this.fechaHora = fechaHora;
        this.tipoServicio = tipoServicio;
        this.nivelRequerido = nivelRequerido;
        this.estado = estado;
        this.puntoOrigen = puntoOrigen;
        this.destinos = destinos;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.longitudTrayecto = longitudTrayecto;
        this.costoTotal = costoTotal;
    }

    public Long getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Long idViaje) {
        this.idViaje = idViaje;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Long getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public PuntoGeograficoEntity getPuntoOrigen() {
        return puntoOrigen;
    }

    public void setPuntoOrigen(PuntoGeograficoEntity puntoOrigen) {
        this.puntoOrigen = puntoOrigen;
    }

    public List<PuntoGeograficoEntity> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<PuntoGeograficoEntity> destinos) {
        this.destinos = destinos;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
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

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }
}
