package uniandes.edu.co.proyecto.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "TARIFAS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TarifaEntity implements Serializable {

    @Id
    private Long idTarifa;      // mapea al _id de Mongo

    private String tipoServicio;
    private String nivel;
    private Double precioPorKm;
    private Date vigenciaDesde;
    private Date vigenciaHasta; // puede ser null

    public TarifaEntity() {
    }

    public TarifaEntity(String tipoServicio, String nivel, Double precioPorKm, Date vigenciaDesde, Date vigenciaHasta) {
        this.tipoServicio = tipoServicio;
        this.nivel = nivel;
        this.precioPorKm = precioPorKm;
        this.vigenciaDesde = vigenciaDesde;
        this.vigenciaHasta = vigenciaHasta;
    }

    public Long getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(Long idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Double getPrecioPorKm() {
        return precioPorKm;
    }

    public void setPrecioPorKm(Double precioPorKm) {
        this.precioPorKm = precioPorKm;
    }

    public Date getVigenciaDesde() {
        return vigenciaDesde;
    }

    public void setVigenciaDesde(Date vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public Date getVigenciaHasta() {
        return vigenciaHasta;
    }

    public void setVigenciaHasta(Date vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }
}
