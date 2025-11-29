package uniandes.edu.co.proyecto.entities;

public class PuntoGeograficoEntity {

    private Long idPunto;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private String direccionAproximada;
    private String ciudad;   // ahora es String, no CiudadEntity

    public PuntoGeograficoEntity() {
    }

    public PuntoGeograficoEntity(Long idPunto, String nombre, Double latitud, Double longitud, String direccionAproximada, String ciudad) {
        this.idPunto = idPunto;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccionAproximada = direccionAproximada;
        this.ciudad = ciudad;
    }

    public Long getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Long idPunto) {
        this.idPunto = idPunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccionAproximada() {
        return direccionAproximada;
    }

    public void setDireccionAproximada(String direccionAproximada) {
        this.direccionAproximada = direccionAproximada;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
