package uniandes.edu.co.proyecto.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "usuarios")
public class UsuarioEntity {

    @Id
    private String id;

    private String nombre;
    private String numeroCelular;
    private String numeroCedula;
    private String correoElectronico;
    private String tipoUsuario;

    private List<Servicio> servicios = new ArrayList<>();
    private List<Viaje> viajes = new ArrayList<>();
    private List<TarjetaCredito> tarjetasCredito = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public UsuarioEntity() {}

    public UsuarioEntity(String nombre, String numeroCelular, String numeroCedula,
                   String correoElectronico, String tipoUsuario) {
        this.nombre = nombre;
        this.numeroCelular = numeroCelular;
        this.numeroCedula = numeroCedula;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipoUsuario;
    }

    public static class Servicio {
        private String tipoServicio;
        private String nivelRequerido;
        private String estado;
        private Integer orden;
        private String restaurante;
        private String puntoOrigen;
        private String puntoDestino;
        private LocalDateTime fechaHora;

        public Servicio() {}

        public Servicio(String tipoServicio, String nivelRequerido, String estado,
                       String puntoOrigen, LocalDateTime fechaHora) {
            this.tipoServicio = tipoServicio;
            this.nivelRequerido = nivelRequerido;
            this.estado = estado;
            this.puntoOrigen = puntoOrigen;
            this.fechaHora = fechaHora;
        }

        public String getTipoServicio() { return tipoServicio; }
        public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

        public String getNivelRequerido() { return nivelRequerido; }
        public void setNivelRequerido(String nivelRequerido) { this.nivelRequerido = nivelRequerido; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public Integer getOrden() { return orden; }
        public void setOrden(Integer orden) { this.orden = orden; }

        public String getRestaurante() { return restaurante; }
        public void setRestaurante(String restaurante) { this.restaurante = restaurante; }

        public String getPuntoOrigen() { return puntoOrigen; }
        public void setPuntoOrigen(String puntoOrigen) { this.puntoOrigen = puntoOrigen; }

        public String getPuntoDestino() { return puntoDestino; }
        public void setPuntoDestino(String puntoDestino) { this.puntoDestino = puntoDestino; }

        public LocalDateTime getFechaHora() { return fechaHora; }
        public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    }

    public static class Viaje {
        private String idConductor;
        private String nivelRequerido;
        private String estado;
        private Integer orden;
        private String restaurante;
        private LocalDateTime fechaHoraInicio;
        private LocalDateTime fechaHoraFin;
        private Double longitudTrayecto;
        private Double costo;

        public Viaje() {}

        public String getIdConductor() { return idConductor; }
        public void setIdConductor(String idConductor) { this.idConductor = idConductor; }

        public String getNivelRequerido() { return nivelRequerido; }
        public void setNivelRequerido(String nivelRequerido) { this.nivelRequerido = nivelRequerido; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public Integer getOrden() { return orden; }
        public void setOrden(Integer orden) { this.orden = orden; }

        public String getRestaurante() { return restaurante; }
        public void setRestaurante(String restaurante) { this.restaurante = restaurante; }

        public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
        public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }

        public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
        public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }

        public Double getLongitudTrayecto() { return longitudTrayecto; }
        public void setLongitudTrayecto(Double longitudTrayecto) { this.longitudTrayecto = longitudTrayecto; }

        public Double getCosto() { return costo; }
        public void setCosto(Double costo) { this.costo = costo; }
    }

    public static class TarjetaCredito {
        private String titularDeLaTarjeta;
        private String numeroTarjeta;
        private LocalDate fechaExpiracion;
        private Integer codigoSeguridad;

        public TarjetaCredito() {}

        public TarjetaCredito(String titularDeLaTarjeta, String numeroTarjeta,
                             LocalDate fechaExpiracion, Integer codigoSeguridad) {
            this.titularDeLaTarjeta = titularDeLaTarjeta;
            this.numeroTarjeta = numeroTarjeta;
            this.fechaExpiracion = fechaExpiracion;
            this.codigoSeguridad = codigoSeguridad;
        }

        public String getTitularDeLaTarjeta() { return titularDeLaTarjeta; }
        public void setTitularDeLaTarjeta(String titularDeLaTarjeta) { this.titularDeLaTarjeta = titularDeLaTarjeta; }

        public String getNumeroTarjeta() { return numeroTarjeta; }
        public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

        public LocalDate getFechaExpiracion() { return fechaExpiracion; }
        public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }

        public Integer getCodigoSeguridad() { return codigoSeguridad; }
        public void setCodigoSeguridad(Integer codigoSeguridad) { this.codigoSeguridad = codigoSeguridad; }
    }

    public static class Review {
        private Double puntuacion;
        private String comentario;
        private LocalDateTime fecha;
        private String idUsuarioPublicado;

        public Review() {}

        public Review(Double puntuacion, String comentario, LocalDateTime fecha, String idUsuarioPublicado) {
            this.puntuacion = puntuacion;
            this.comentario = comentario;
            this.fecha = fecha;
            this.idUsuarioPublicado = idUsuarioPublicado;
        }

        public Double getPuntuacion() { return puntuacion; }
        public void setPuntuacion(Double puntuacion) { this.puntuacion = puntuacion; }

        public String getComentario() { return comentario; }
        public void setComentario(String comentario) { this.comentario = comentario; }

        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

        public String getIdUsuarioPublicado() { return idUsuarioPublicado; }
        public void setIdUsuarioPublicado(String idUsuarioPublicado) { this.idUsuarioPublicado = idUsuarioPublicado; }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumeroCelular() { return numeroCelular; }
    public void setNumeroCelular(String numeroCelular) { this.numeroCelular = numeroCelular; }

    public String getNumeroCedula() { return numeroCedula; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public List<Servicio> getServicios() { return servicios; }
    public void setServicios(List<Servicio> servicios) { this.servicios = servicios; }

    public List<Viaje> getViajes() { return viajes; }
    public void setViajes(List<Viaje> viajes) { this.viajes = viajes; }

    public List<TarjetaCredito> getTarjetasCredito() { return tarjetasCredito; }
    public void setTarjetasCredito(List<TarjetaCredito> tarjetasCredito) { this.tarjetasCredito = tarjetasCredito; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
