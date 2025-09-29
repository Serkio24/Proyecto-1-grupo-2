package uniandes.edu.co.proyecto.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="servicios")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idServicio;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double longitudTrayecto;
    private Double costoTotal;
    private String estado;
    private Double comision;
    private String tipoServicio;
    private String nivel;      // [0..1]
    private String orden;      // [0..1]
    private String restaurante; // [0..1]

    // Constructor con parámetros
    public ServicioEntity(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                          Double longitudTrayecto, Double costoTotal, String estado,
                          Double comision, String tipoServicio, String nivel,
                          String orden, String restaurante) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.longitudTrayecto = longitudTrayecto;
        this.costoTotal = costoTotal;
        this.estado = estado;
        this.comision = comision;
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
        this.orden = orden;
        this.restaurante = restaurante;
    }

    // Constructor vacío
    public ServicioEntity() {}

    // Getters
    public Long getIdServicio() {
        return idServicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public Double getLongitudTrayecto() {
        return longitudTrayecto;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public Double getComision() {
        return comision;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public String getNivel() {
        return nivel;
    }

    public String getOrden() {
        return orden;
    }

    public String getRestaurante() {
        return restaurante;
    }

    // Setters
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setLongitudTrayecto(Double longitudTrayecto) {
        this.longitudTrayecto = longitudTrayecto;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }
}
