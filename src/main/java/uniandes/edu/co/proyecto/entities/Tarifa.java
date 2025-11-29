package uniandes.edu.co.proyecto.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tarifas")
public class Tarifa {

    @Id
    private String id;

    private String tipoServicio;
    private String nivel;
    private Double precioPorKM;

    // Constructores
    public Tarifa() {}

    public Tarifa(String tipoServicio, String nivel, Double precioPorKM) {
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
        this.precioPorKM = precioPorKM;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Double getPrecioPorKM() { return precioPorKM; }
    public void setPrecioPorKM(Double precioPorKM) { this.precioPorKM = precioPorKM; }
}
