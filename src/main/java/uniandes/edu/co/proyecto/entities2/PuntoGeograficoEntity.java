package uniandes.edu.co.proyecto.entities2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "puntos_geograficos")
public class PuntoGeograficoEntity {

    @Id
    private String id;

    private String nombre;
    private Double latitud;
    private Double longitud;
    private String direccionAproximada;
    private String ciudad;

    public PuntoGeograficoEntity() {}

    public PuntoGeograficoEntity(String nombre, Double latitud, Double longitud,
                          String direccionAproximada, String ciudad) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccionAproximada = direccionAproximada;
        this.ciudad = ciudad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public String getDireccionAproximada() { return direccionAproximada; }
    public void setDireccionAproximada(String direccionAproximada) { this.direccionAproximada = direccionAproximada; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}
