package uniandes.edu.co.proyecto.entities;

public class TarjetaCreditoEntity {

    private Long idTarjetaCredito;
    private String titularDeLaTarjeta;
    private String numeroTarjeta;
    // En el modelo Mongo está como string "AAAA-MM"
    private String fechaExpiracion;
    private Integer codigoSeguridad;

    public TarjetaCreditoEntity() {
    }

    public TarjetaCreditoEntity(Long idTarjetaCredito,
                                String titularDeLaTarjeta,
                                String numeroTarjeta,
                                String fechaExpiracion,
                                Integer codigoSeguridad) {
        this.idTarjetaCredito = idTarjetaCredito;
        this.titularDeLaTarjeta = titularDeLaTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.codigoSeguridad = codigoSeguridad;
    }

    public Long getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Long idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public String getTitularDeLaTarjeta() {
        return titularDeLaTarjeta;
    }

    public void setTitularDeLaTarjeta(String titularDeLaTarjeta) {
        this.titularDeLaTarjeta = titularDeLaTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Integer getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(Integer codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
}
